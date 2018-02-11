package com.mm.dev.fastdfs;

import java.io.Serializable;

public interface FileManagerConfig extends Serializable {

    public static final String FILE_DEFAULT_AUTHOR = "ivplay";

    public static final String PROTOCOL = "http://";

    public static final String SEPARATOR = "/";

    public static final String TRACKER_NGNIX_ADDR = "39.108.230.90";

    public static final String TRACKER_NGNIX_PORT = ":8888";

    public static final String CLIENT_CONFIG_FILE = "client.conf";
}