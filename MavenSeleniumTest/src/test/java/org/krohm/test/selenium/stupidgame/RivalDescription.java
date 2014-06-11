/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium.stupidgame;

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
    // player only
    private int cash;
    private int energy;
    private int energy_lim;
    private int stamina;
    private int stamina_lim;
    private int gold;

    @Override
    public String toString() {
        return "[" + attack + "/" + defense + "]<" + id + "> Rival named <" + name + "> has <" + itemNumber + "> items.";
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina_lim() {
        return stamina_lim;
    }

    public void setStamina_lim(int stamina_lim) {
        this.stamina_lim = stamina_lim;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy_lim() {
        return energy_lim;
    }

    public void setEnergy_lim(int energy_lim) {
        this.energy_lim = energy_lim;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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
