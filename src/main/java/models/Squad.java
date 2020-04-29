package models;

import java.util.Objects;

public class Squad {

    private int id;
    private String squadName;
    private String jerseyColor;
    private String strategy;

    public Squad(String squadName, String jerseyColor, String strategy) {
        this.squadName = squadName;
        this.jerseyColor = jerseyColor;
        this.strategy = strategy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }

    public String getJerseyColor() {
        return jerseyColor;
    }

    public void setJerseyColor(String jerseyColor) {
        this.jerseyColor = jerseyColor;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squad squad = (Squad) o;
        return id == squad.id &&
                squadName.equals(squad.squadName) &&
                jerseyColor.equals(squad.jerseyColor) &&
                strategy.equals(squad.strategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, squadName, jerseyColor, strategy);
    }
}

