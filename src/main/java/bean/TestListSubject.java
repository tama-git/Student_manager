package bean;

import java.util.HashMap;
import java.util.Map;

public class TestListSubject implements java.io.Serializable {
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private Map<Integer, Integer> points = new HashMap<>();



    public int getEntYear() {
        return entYear;
    }
    public String getStudentNo() {
        return studentNo;
    }
 
    public String getStudentName() {
        return studentName;
    }
    
    public String getClassNum() {
        return classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }
    
    
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }
    
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    // 特定の回数（key）の点数を取得するメソッド
    public String getPoint(int key) {
        Integer point = points.get(key);
        // Mapに値がない場合は空文字を返す、などの処理をここで行えます
        return (point == null) ? "" : String.valueOf(point);
    }

    // 特定の回数（key）に点数（value）をセットするメソッド
    public void putPoint(int key, int value) {
        this.points.put(key, value);
    }
}