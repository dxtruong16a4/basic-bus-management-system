package utility;

import java.util.*;
import java.awt.Component;

public class ComponentManager {
    public static ComponentManager instance;
    private Map<String, List<Component>> componentMap;
    private Map<String, Component> componentById;

    public static ComponentManager getInstance() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }

    private ComponentManager() {
        componentMap = new HashMap<>();
        componentMap.put("label", new ArrayList<>());
        componentMap.put("textfield", new ArrayList<>());
        componentMap.put("combobox", new ArrayList<>());
        componentMap.put("spinner", new ArrayList<>());
        componentMap.put("truebutton", new ArrayList<>());
        componentMap.put("falsebutton", new ArrayList<>());
        componentMap.put("calendar", new ArrayList<>());
        componentMap.put("component", new ArrayList<>());
        componentById = new HashMap<>();
    }

    public List<Component> getComponentList(String key) {
        return componentMap.getOrDefault(key.toLowerCase(), new ArrayList<>());
    }

    public void addComponent(String key, Component component) {
        componentMap.computeIfAbsent(key.toLowerCase(), k -> new ArrayList<>()).add(component);
    }

    public void addComponentWithId(String id, String key, Component component) {
        componentMap.computeIfAbsent(key.toLowerCase(), k -> new ArrayList<>()).add(component);
        if (id != null && !id.isEmpty()) {
            componentById.put(id, component);
        }
    }

    public Component getComponentById(String id) {
        return componentById.get(id);
    }

    public void clearAllComponents() {
        for (List<Component> list : componentMap.values()) {
            list.clear();
        }
        componentById.clear();
    }

    public void clearComponentList(String key) {
        List<Component> list = componentMap.get(key.toLowerCase());
        if (list != null) {
            list.clear();
        } else {
            throw new IllegalArgumentException("Unknown component type: " + key);
        }
    }

    public void removeComponentById(String id) {
        Component c = componentById.remove(id);
        if (c != null) {
            for (List<Component> list : componentMap.values()) {
                list.remove(c);
            }
        }
    }
}
