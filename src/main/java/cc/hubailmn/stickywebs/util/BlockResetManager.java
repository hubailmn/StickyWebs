package cc.hubailmn.stickywebs.util;

import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class BlockResetManager {

    public Set<UUID> bypassList = ConcurrentHashMap.newKeySet();

    public void addBypass(UUID uuid) {
        bypassList.add(uuid);
    }

    public void removeBypass(UUID uuid) {
        bypassList.remove(uuid);
    }

    public boolean toggleBypass(UUID uuid) {
        if (bypassList.contains(uuid)) {
            bypassList.remove(uuid);
            return false;
        } else {
            bypassList.add(uuid);
            return true;
        }
    }

    public boolean isBypass(UUID uuid) {
        return bypassList.contains(uuid);
    }

    public void clearBypass() {
        bypassList.clear();
    }

}
