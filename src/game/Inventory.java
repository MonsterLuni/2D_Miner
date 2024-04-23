package game;

import game.Entity.Entity;
import game.Entity.InventoryItem;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
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
    public LinkedHashMap<Point, InventoryItem> inventory;
    public void sortInventory(Point current) {
        for (java.util.Map.Entry<Point, InventoryItem> entry : inventory.entrySet()) {
            if(Objects.equals(entry.getValue().entity.getName(), inventory.get(current).entity.getName()) && entry.getKey() != current){
                if(entry.getValue().amount + inventory.get(current).amount <= entry.getValue().entity.stackSize){
                    entry.setValue(new InventoryItem(inventory.get(current).entity, entry.getValue().amount + inventory.get(current).amount));
                    inventory.remove(current);
                    break;
                }
                else{
                    inventory.replace(new Point(1,1),new InventoryItem(inventory.get(current).entity, (entry.getValue().amount - entry.getValue().entity.stackSize) + inventory.get(current).amount));
                    entry.setValue(new InventoryItem(entry.getValue().entity,entry.getValue().entity.stackSize));
                }
            }
        }
    }
    public Point getFirstFreeInventorySpace(){
        int lastX = 0;
        int lastY = 0;
        Point point = new Point(lastX,lastY);
        for (java.util.Map.Entry<Point, InventoryItem> entry : inventory.entrySet()) {
            if(entry.getKey().x == lastX && entry.getKey().y == lastY){
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
        Map.Entry<Point, InventoryItem> firstEntry = null;
        Map.Entry<Point, InventoryItem> secondEntry = null;
        for (java.util.Map.Entry<Point, InventoryItem> entry : inventory.entrySet()) {
            if(entry.getKey().x == activeInventorySpace.x && entry.getKey().y == activeInventorySpace.y){
                firstEntry = entry;
            } else if (entry.getKey().x == activeInventorySpaceTwo.x && entry.getKey().y == activeInventorySpaceTwo.y) {
                secondEntry = entry;
            }
        }
        if(firstEntry != null && secondEntry != null){
            InventoryItem swap = firstEntry.getValue();
            inventory.replace(firstEntry.getKey(),secondEntry.getValue());
            inventory.replace(secondEntry.getKey(),swap);
        }
        else if (firstEntry != null){
            inventory.replace(activeInventorySpaceTwo,firstEntry.getValue());
            inventory.remove(firstEntry.getKey());
        }
    }
    public void splitItemInInventory(){
        Map.Entry<Point, InventoryItem> firstEntry = null;
        Map.Entry<Point, InventoryItem> secondEntry = null;
        for (java.util.Map.Entry<Point, InventoryItem> entry : inventory.entrySet()) {
            if(entry.getKey().x == activeInventorySpace.x && entry.getKey().y == activeInventorySpace.y){
                firstEntry = entry;
            } else if (entry.getKey().x == activeInventorySpaceTwo.x && entry.getKey().y == activeInventorySpaceTwo.y) {
                secondEntry = entry;
            }
        }
        if(firstEntry != null && secondEntry == null){
            if(firstEntry.getValue().amount % 2 == 0){
                inventory.replace(firstEntry.getKey(),new InventoryItem(firstEntry.getValue().entity, firstEntry.getValue().amount/2));
                inventory.put(firstEntry.getKey(),new InventoryItem(firstEntry.getValue().entity, firstEntry.getValue().amount/2));
                System.out.println("Gerade");
            }
            else{
                inventory.replace(firstEntry.getKey(),new InventoryItem(firstEntry.getValue().entity, firstEntry.getValue().amount/2 - 1));
                inventory.put(firstEntry.getKey(),new InventoryItem(firstEntry.getValue().entity, firstEntry.getValue().amount/2 + 1));
                System.out.println("Ungerade");
            }
        }
    }
    public void swapInventories(Inventory inventory2){
        Entity entityInv = null;
        Entity entityInv2 = null;
        int inventoryInt = 0;
        int inventoryInt2 = 0;
        for (java.util.Map.Entry<Point, InventoryItem> entry : inventory.entrySet()) {
            if(entry.getKey().inventoryX == inventorySpaceX && entry.getKey().inventoryY == inventorySpaceY){
                entityInv = entry.getKey();
                inventoryInt = entry.getValue();
            }
        }
        for (java.util.Map.Entry<Point, InventoryItem> entry : inventory2.inventory.entrySet()) {
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
}

