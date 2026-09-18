
# AdGuard Home Operator

A Kubernetes operator that keeps AdGuard Home DNS rewrites in sync with your Kubernetes cluster.

Create an `AdGuardDnsRewrite` resource and the operator adds the corresponding rewrite to AdGuard Home. When the resource is deleted, the operator removes the rewrite again.

## How it works

The operator watches the `AdGuardDnsRewrite` custom resource in Kubernetes and talks to the AdGuard Home control API.

```text
AdGuardDnsRewrite
        |
        v
Kubernetes operator  ---- HTTP API ---->  AdGuard Home
```

Each resource defines a domain and the IP address returned for that domain.

## Requirements

- A Kubernetes cluster with permission to install a CRD and cluster-scoped RBAC
- AdGuard Home with access to its control API
- Java 25 for local development
- Maven 3.9+ (or the included Maven Wrapper)

## Install in Kubernetes

The repository includes a starting point at [`k8s/install/scaffold.yaml`](k8s/install/scaffold.yaml). Before applying it:

1. Replace the operator image with an image available to your cluster.
2. Set `CLIENT_BASE_URL` to the AdGuard Home control API URL, including the `/control/` path.
3. Set `ADGUARD_USERNAME` and create the `adguardhome-password` Secret with the matching password.
4. Review the namespace, RBAC rules, and image pull policy for your cluster.

Apply the installation manifest:

```sh
kubectl apply -f k8s/install/scaffold.yaml
```

The manifest uses the `default` ServiceAccount in the `adguardhome-operator` namespace. For production use, consider creating a dedicated ServiceAccount and narrowing the RBAC permissions.

## Create a DNS rewrite

```yaml
apiVersion: com.matman0497/v1
kind: AdGuardDnsRewrite
metadata:
  name: home-server
spec:
  domain: home.example.com
  ipAddress: 192.168.1.10
```

Save the resource and apply it:

```sh
kubectl apply -f rewrite.yaml
kubectl get adguarddnsrewrites
```

Deleting the resource triggers cleanup in AdGuard Home:

```sh
kubectl delete -f rewrite.yaml
```

## Configuration

The operator reads these environment variables:

| Variable | Required | Description |
| --- | --- | --- |
| `CLIENT_BASE_URL` | No | AdGuard Home control API URL. Defaults to `http://localhost:8000/control/`. |
| `ADGUARD_USERNAME` | Yes | AdGuard Home username. |
| `ADGUARD_PASSWORD` | Yes | AdGuard Home password. |

## Local development

Start AdGuard Home locally with Docker Compose:

```sh
docker compose up -d
```

The included Compose file exposes the AdGuard Home web interface on [http://localhost](http://localhost) and the initial setup interface on [http://localhost:3000](http://localhost:3000). Complete AdGuard Home setup before starting the operator.

Build the project:

```sh
./mvnw clean verify
```

On Windows PowerShell:

```powershell
.\mvnw.cmd clean verify
```

Run the packaged operator with the required configuration:

```sh
CLIENT_BASE_URL=http://localhost:8000/control/ \
ADGUARD_USERNAME=admin \
ADGUARD_PASSWORD='your-password' \
java -jar operator/target/adguard-home-operator-reconcile-0.0.1-SNAPSHOT.jar
```
