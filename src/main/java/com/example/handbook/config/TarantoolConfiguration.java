package com.example.handbook.config;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolClientConfig;
import io.tarantool.driver.api.TarantoolClusterAddressProvider;
import io.tarantool.driver.api.TarantoolServerAddress;
import io.tarantool.driver.api.retry.TarantoolRequestRetryPolicies;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import io.tarantool.driver.core.ClusterTarantoolClient;
import io.tarantool.driver.core.ClusterTarantoolTupleClient;
import io.tarantool.driver.core.ProxyTarantoolTupleClient;
import io.tarantool.driver.core.RetryingTarantoolTupleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Configuration
public class TarantoolConfiguration {

    @Value("${tarantool.url}")
    protected List<String> url;

    @Value("${tarantool.username}")
    protected String username;

    @Value("${tarantool.password}")
    protected String password;

    @Bean
    public TarantoolClient tarantoolClient() {
        SimpleTarantoolCredentials credentials = new SimpleTarantoolCredentials(username, password);

        TarantoolClientConfig config = new TarantoolClientConfig.Builder()
                .withCredentials(credentials)
                .withRequestTimeout(1000 * 60)
                .build();

        TarantoolClusterAddressProvider provider = () -> url
                .stream()
                .map(server -> server.split(":"))
                .map(arr -> new TarantoolServerAddress(arr[0], Integer.parseInt(arr[1])))
                .collect(toList());

        ClusterTarantoolClient clusterTarantoolClient = new ClusterTarantoolTupleClient(config, provider);
        return new RetryingTarantoolTupleClient(
                new ProxyTarantoolTupleClient(clusterTarantoolClient),
                TarantoolRequestRetryPolicies.byNumberOfAttempts(
                        10, e -> e.getMessage().contains("Unsuccessfull attempt")
                ).build());
    }
}
