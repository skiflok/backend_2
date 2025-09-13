# Troubleshooting (частые ошибки)

##

### java.lang.NullPointerException: Cannot invoke "jdk.internal.platform.CgroupInfo.getMountPoint()"

**Ошибка:**

```
java.lang.NullPointerException: Cannot invoke "jdk.internal.platform.CgroupInfo.getMountPoint()" 
```

возникает при запуске сервиса в Docker с **cgroups v2** на базе образа с **JDK 17**.

**Решение:**
Перейти на **JDK 21** в `jib-maven-plugin` (или Dockerfile):

```xml
<from>
    <image>eclipse-temurin:21-jre</image>
</from>
```
