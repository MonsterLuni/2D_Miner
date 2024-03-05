package game;

import game.Entity.Entity;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Inventory implements Serializable{
    public Point activeInventorySpace = new Point(-1,-1);
    public Point activeInventorySpaceTwo = new Point(-1,-1);
    public int inventorySpaceX;
    public int inventorySpaceY;
    public int height;
    public int width;
    public int maxSize;
    public Inventory(int height, int width){
        this.height = height * 25;
        this.width = width * 25;
        this.maxSize = height*width;
        inventory = new LinkedHashMap<>(maxSize);
    }
    public LinkedHashMap<Entity, Integer> inventory;
    public void sortInventory(Entity current) {
        for (java.util.Map.Entry<Entity, Integer> entry : inventory.entrySet()) {
            if(Objects.equals(entry.getKey().getName(), current.getName()) && entry.getKey() != current){
                if(entry.getValue() + inventory.get(current) <= entry.getKey().stackSize){
                    entry.setValue(entry.getValue() + inventory.get(current));
                    inventory.remove(current);
                    break;
                }
                else{
                    inventory.replace(current, (entry.getValue() - entry.getKey().stackSize) + inventory.get(current));
                    entry.setValue(entry.getKey().stackSize);
                }
            }
        }
    }
    public Point getFirstFreeInventorySpace(){
        int lastX = 0;
        int lastY = 0;
        Point point = new Point(lastX,lastY);
        for (java.util.Map.Entry<Entity, Integer> entry : inventory.entrySet()) {
            if(entry.getKey().inventoryX == lastX && entry.getKey().inventoryY == lastY){
                lastX++;
            }
            if(lastX >= 10){
                lastY++;
                lastX = 0;
            }
            point = new Point(lastX,lastY);
        }
        return point;
    }
    public void changeItemInInventory(){
        Entity firstEntry = null;
        Entity secondEntry = null;
        for (java.util.Map.Entry<Entity, Integer> entry : inventory.entrySet()) {
            if(entry.getKey().inventoryX == activeInventorySpace.x && entry.getKey().inventoryY == activeInventorySpace.y){
                firstEntry = entry.getKey();
            } else if (entry.getKey().inventoryX == activeInventorySpaceTwo.x && entry.getKey().inventoryY == activeInventorySpaceTwo.y) {
                secondEntry = entry.getKey();
            }
        }
        if(firstEntry != null && secondEntry != null){
            int swapX = firstEntry.inventoryX;
            int swapY = firstEntry.inventoryY;
            firstEntry.inventoryX = secondEntry.inventoryX;
            firstEntry.inventoryY = secondEntry.inventoryY;
            secondEntry.inventoryX = swapX;
            secondEntry.inventoryY= swapY;
        }
        else if (firstEntry != null){
            firstEntry.inventoryX = activeInventorySpaceTwo.x;
            firstEntry.inventoryY = activeInventorySpaceTwo.y;
        }
    }
    public void swapInventories(Inventory inventory2){
        Entity entityInv = null;
        Entity entityInv2 = null;
        int inventoryInt = 0;
        int inventoryInt2 = 0;
        for (java.util.Map.Entry<Entity, Integer> entry : inventory.entrySet()) {
            if(entry.getKey().inventoryX == inventorySpaceX && entry.getKey().inventoryY == inventorySpaceY){
                entityInv = entry.getKey();
                inventoryInt = entry.getValue();
            }
        }
        for (java.util.Map.Entry<Entity, Integer> entry : inventory2.inventory.entrySet()) {
            if(entry.getKey().inventoryX == inventory2.inventorySpaceX && entry.getKey().inventoryY == inventory2.inventorySpaceY){
                entityInv2 = entry.getKey();
                inventoryInt2 = entry.getValue();
            }
        }
        if(entityInv != null && entityInv2 != null){
            int invX = entityInv.inventoryX;
            int invY = entityInv.inventoryY;
            entityInv.inventoryX = entityInv2.inventoryX;
            entityInv.inventoryY = entityInv2.inventoryY;
            entityInv2.inventoryX = invX;
            entityInv2.inventoryY = invY;
            inventory.put(entityInv2,inventoryInt2);
            inventory.remove(entityInv);
            inventory2.inventory.put(entityInv,inventoryInt);
            inventory2.inventory.remove(entityInv2);
        }
        else if (entityInv != null){
            entityInv.inventoryX = inventory2.inventorySpaceX;
            entityInv.inventoryY = inventory2.inventorySpaceY;
            inventory2.inventory.put(entityInv,inventoryInt);
            inventory.remove(entityInv);
        }
        else if(entityInv2 != null){
            entityInv2.inventoryX = inventorySpaceX;
            entityInv2.inventoryY = inventorySpaceY;
            inventory.put(entityInv2,inventoryInt2);
            inventory2.inventory.remove(entityInv2);
        }
    }
    public Entity getKeyFromCoordinates(int x, int y) {
        for (java.util.Map.Entry<Entity, Integer> entry : inventory.entrySet()) {
            if(entry.getKey().inventoryX == x && entry.getKey().inventoryY == y){
                return entry.getKey();
            }
        }
        return null;
    }
    public int getValueFromCoordinates(int x, int y) {
        for (java.util.Map.Entry<Entity, Integer> entry : inventory.entrySet()) {
            if(entry.getKey().inventoryX == x && entry.getKey().inventoryY == y){
                return entry.getValue();
            }
        }
        return -1;
    }
}
