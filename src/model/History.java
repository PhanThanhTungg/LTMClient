
package model;

import java.sql.Timestamp;

public class History {
    private int id, idUser1, idUser2, scoreUser1, scoreUser2;
    private String result,nameUser2,time;

    public History() {
    }

    public History(int id, int idUser1, int idUser2, int scoreUser1, int scoreUser2, String time) {
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.scoreUser1 = scoreUser1;
        this.scoreUser2 = scoreUser2;
        this.time = time;
    }
    
    
    public History(String nameUser2, int scoreUser1, int scoreUser2, String time) {
        this.nameUser2 = nameUser2;
        this.scoreUser1 = scoreUser1;
        this.scoreUser2 = scoreUser2;
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNameUser2() {
        return nameUser2;
    }

    public void setNameUser2(String nameUser2) {
        this.nameUser2 = nameUser2;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }

    public int getScoreUser1() {
        return scoreUser1;
    }

    public void setScoreUser1(int scoreUser1) {
        this.scoreUser1 = scoreUser1;
    }

    public int getScoreUser2() {
        return scoreUser2;
    }

    public void setScoreUser2(int scoreUser2) {
        this.scoreUser2 = scoreUser2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
    
    
    