package com.matman0497;

import com.matman0497.actions.Rewrite;
import com.matman0497.requests.AddRewrite;
import com.matman0497.requests.DeleteRewrite;
import io.javaoperatorsdk.operator.api.reconciler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AdguardHomeOperatorReconciler implements Reconciler<AdGuardDnsRewrite>, Cleaner<AdGuardDnsRewrite> {
    private static final Logger log = LoggerFactory.getLogger(AdguardHomeOperatorReconciler.class);

    private final Rewrite adGuardClient;

    public AdguardHomeOperatorReconciler(Rewrite adGuardClient) {
        this.adGuardClient = adGuardClient;
    }

    public UpdateControl<AdGuardDnsRewrite> reconcile(AdGuardDnsRewrite primary,
                                                      Context<AdGuardDnsRewrite> context) throws IOException, InterruptedException {
        log.debug(primary.getSpec().getDomain());
        log.debug(primary.getSpec().getIpAddress());

        adGuardClient.add(new AddRewrite(primary.getSpec().getDomain(), primary.getSpec().getIpAddress(), true));

        log.debug("Reconciling...");

        return UpdateControl.noUpdate();
    }

    @Override
    public DeleteControl cleanup(AdGuardDnsRewrite resource, Context<AdGuardDnsRewrite> context) throws Exception {

        log.debug("Cleanup...");

        this.adGuardClient.delete(new DeleteRewrite(resource.getSpec().getDomain(), resource.getSpec().getIpAddress(), true));

        return DeleteControl.defaultDelete();
    }
}
