package datos.access.result;

public class InsertResult {
    private int affectedRows;
    private int insertedId;
    
    private InsertResult() {
        
    }
    
    public InsertResult(int affectedRows, int insertedId) {
        this.affectedRows = affectedRows;
        this.insertedId = insertedId;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }

    public int getInsertedId() {
        return insertedId;
    }

    public void setInsertedId(int insertedId) {
        this.insertedId = insertedId;
    }
    
    
}
