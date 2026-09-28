# Nexus Repository Setup & Configuration Guide

This guide explains how to set up, initialize, and configure **Sonatype Nexus 3** for hosting and deploying Maven artifacts (`employee-library-1.0.0.jar`).

---

## 1. Start Nexus and Redis via Docker Compose

From the project root:
```bash
docker compose -f docker/docker-compose.yml up -d
```
Nexus will start and expose its Web UI at:
👉 **http://localhost:8081**

> **Note**: Nexus takes approximately 1 to 2 minutes to fully initialize. You can verify its health via:
> ```bash
> docker logs -f employee-nexus
> ```
> Wait until you see: `Started Sonatype Nexus OSS`.

---

## 2. Retrieve Initial Admin Password

When Nexus starts for the first time, an initial random password is generated inside the container.
Retrieve it with:
```bash
docker exec -it employee-nexus cat /nexus-data/admin.password
```

1. Open **http://localhost:8081** in your browser.
2. Click **Sign In** (top right).
3. Username: `admin`
4. Password: `<paste retrieved password>`
5. Follow the setup wizard:
   - Set a new password (e.g. `admin123` to match `maven-settings/settings.xml`).
   - Enable **Anonymous access** (recommended for evaluation simplicity).

---

## 3. Verify Default Hosted Repositories

Nexus 3 comes with pre-configured Maven repositories out of the box:
- `maven-releases` (Hosted, Release version policy) -> `http://localhost:8081/repository/maven-releases/`
- `maven-snapshots` (Hosted, Snapshot version policy) -> `http://localhost:8081/repository/maven-snapshots/`
- `maven-public` (Group repository combining releases, snapshots, and Maven central)

### Ensure Deployment Permissions:
1. Navigate to **Server Admin and configuration (gear icon)** -> **Repository** -> **Repositories**.
2. Click on `maven-releases`.
3. Under **Hosted** -> **Deployment policy**, ensure it is set to **Allow redeploy** (allows re-deploying during development).
4. Click **Save**.

---

## 4. Deploying Artifacts via Maven

To deploy `employee-library`:
```bash
cd employee-library
mvn clean deploy -s ../maven-settings/settings.xml
```

Once executed, verify that `employee-library-1.0.0.jar` and its `pom.xml` appear under:
**Browse** -> **Browse** -> **maven-releases** -> `com` -> `nashtech` -> `employee-library` -> `1.0.0`.

