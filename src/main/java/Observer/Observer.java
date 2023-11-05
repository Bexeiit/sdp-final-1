package Observer;

import java.sql.SQLException;

public interface Observer {
    void handleEvent() throws SQLException;
}
