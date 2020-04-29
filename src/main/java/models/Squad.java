package models;

import java.util.Objects;

public class Squad {

    private int id;
    private String squadName;
    private String jersey;
    private String strategy;

    public Squad(String squadName, String jersey, String strategy) {
        this.squadName = squadName;
        this.jersey = jersey;
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

    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
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
                jersey.equals(squad.jersey) &&
                strategy.equals(squad.strategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, squadName, jersey, strategy);
    }
}

