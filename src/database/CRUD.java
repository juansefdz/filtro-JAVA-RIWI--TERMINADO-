package database;
import java.util.List;

public interface CRUD {


    public Object insert (Object object);

    public List<Object> findAll();
    public boolean delete (Object object);
    public  boolean update (Object object);
}
