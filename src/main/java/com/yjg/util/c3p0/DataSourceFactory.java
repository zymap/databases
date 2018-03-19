package com.yjg.util.c3p0;

import javax.activation.DataSource;
import java.util.Properties;

/**
 * Created by evan on 17-9-3.
 */
public interface DataSourceFactory {
    void setProperties(Properties properties);
    DataSource getDataSource();
}
