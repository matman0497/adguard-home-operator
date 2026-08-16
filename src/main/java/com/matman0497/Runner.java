package com.matman0497;

import com.matman0497.actions.Rewrite;
import com.matman0497.auth.BasicAuth;
import io.javaoperatorsdk.operator.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;


public class Runner {

    private static final Logger log = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {

        var client = new Client(HttpClient.newBuilder().build(), new BasicAuth("admin", "adminadmin"));

        var rewrite = new Rewrite(client);

        Operator operator = new Operator();
        operator.register(new AdguardHomeOperatorReconciler(rewrite));
        operator.start();
        log.info("Operator started.");
    }
}
