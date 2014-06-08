/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium;

/**
 *
 * @author Arnaud
 */
public class RivalDescription {

    private int defense = Integer.MAX_VALUE;
    private String name = null;
    private int itemNumber = -1;
    private int attack = Integer.MAX_VALUE;
    private int id = -1;

    @Override
    public String toString() {
        return "[" + attack + "/" + defense + "]<" + id + "> Rival named <" + name + "> has <" + itemNumber + "> items.";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
}
