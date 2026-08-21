package com.matman0497;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("com.matman0497")
@Version("v1")
public class AdGuardDnsRewrite extends CustomResource<AdGuardDnsRewriteSpec,AdguardHomeOperatorStatus> implements Namespaced {
}
