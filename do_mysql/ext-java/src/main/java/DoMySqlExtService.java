import data_objects.drivers.DriverDefinition;
import do_mysql.MySqlDriverDefinition;

public class DoMySqlExtService extends AbstractDataObjectsExtService {

    private final static DriverDefinition driver = new MySqlDriverDefinition();
    public final static String RUBY_MODULE_NAME = "MySql";
    public final static String RUBY_ERROR_NAME = "MySqlError";

    public String getModuleName() {
        return RUBY_MODULE_NAME;
    }

    @Override
    public String getErrorName() {
        return RUBY_ERROR_NAME;
    }

    public DriverDefinition getDriverDefinition() {
        return driver;
    }

}
