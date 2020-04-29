package models;

import java.util.Objects;

public class Player {

    private int id;
    private String name;
    private int age;
    private String position;
    private String dominantHand;
    private int squadId;

    public Player(String name, int age, String position, String dominantHand, int squadId) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.dominantHand = dominantHand;
        this.squadId = squadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                age == player.age &&
                squadId == player.squadId &&
                name.equals(player.name) &&
                position.equals(player.position) &&
                dominantHand.equals(player.dominantHand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, position, dominantHand, squadId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDominantHand() {
        return dominantHand;
    }

    public void setDominantHand(String dominantHand) {
        this.dominantHand = dominantHand;
    }

    public int getSquadId() {
        return squadId;
    }

    public void setSquadId(int squadId) {
        this.squadId = squadId;
    }
}
