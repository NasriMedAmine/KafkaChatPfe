## 👥 User Roles

Users are assigned one of the following roles:

### Group 1
- `MODERATEUR`
- `SOURCING`
- `COMMERCE`
- `ADV`

### Group 2
- `CLIENT`
- `CANDIDAT`
- `AMBASSADEUR`
- `SOURCING_COMMERCE`
- `AUTRE`

### Special Role
- `ADMIN`

---

## 🔐 Chat Permission Rules

The method `canChat(String roleA, String roleB)` defines the logic:

```java
private boolean canChat(String roleA, String roleB) {
    List<String> group1 = List.of("MODERATEUR", "SOURCING", "COMMERCE", "ADV");
    List<String> group2 = List.of("CLIENT", "CANDIDAT", "AMBASSADEUR", "SOURCING_COMMERCE", "AUTRE");

    // Group 1 ↔ Group 1
    if (group1.contains(roleA) && group1.contains(roleB)) return true;

    // Group 1 ↔ Group 2
    if ((group1.contains(roleA) && group2.contains(roleB)) ||
        (group2.contains(roleA) && group1.contains(roleB))) return true;

    // Admin ↔ Group 1
    if ((roleA.equals("ADMIN") && group1.contains(roleB)) ||
        (roleB.equals("ADMIN") && group1.contains(roleA))) return true;

    // All other combinations are not allowed
    return false;
}




## ✅ Chat Permission Matrix

| Sender Role | Receiver Role | Allowed |
|-------------|----------------|---------|
| `ADMIN`     | `SOURCING`     | ✅ Yes  |
| `MODERATEUR`| `CLIENT`       | ✅ Yes  |
| `CLIENT`    | `CANDIDAT`     | ✅ Yes  |
| `CANDIDAT`  | `ADMIN`        | ❌ No   |
| `ADV`       | `AMBASSADEUR`  | ✅ Yes  |
| `AUTRE`     | `MODERATEUR`   | ✅ Yes  |
