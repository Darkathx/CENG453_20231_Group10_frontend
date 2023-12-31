package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.EnumMap;
import java.util.Map;

public class ResourceCardDeck {
    private final Map<ResourceType, Integer> resources;

    public ResourceCardDeck() {
        resources = new EnumMap<>(ResourceType.class);
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, 0);
        }
    }

    public void addResource(ResourceType type, int amount) {
        resources.put(type, resources.getOrDefault(type, 0) + amount);
    }

    public void setResource(ResourceType type, int amount) {
        resources.put(type, amount);
    }

    public boolean canDeduct(Map<ResourceType, Integer> cost) {
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            if (resources.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void deduct(Map<ResourceType, Integer> cost) {
        if (canDeduct(cost)) {
            for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
                int currentAmount = resources.get(entry.getKey());
                resources.put(entry.getKey(), currentAmount - entry.getValue());
            }
        }
    }

    public int getResourceCount(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }
}
