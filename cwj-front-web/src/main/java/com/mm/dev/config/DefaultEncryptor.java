package com.mm.dev.config;

import org.jasypt.encryption.StringEncryptor;


public class DefaultEncryptor implements StringEncryptor {
	
//	private static final String SECURITY_PROPERTIES_FILE = "security.properties";

//    @Bean
//    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        encryptor.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
//        encryptor.setPassword("security");
//        EncryptablePropertySourcesPlaceholderConfigurer configurer = new EncryptablePropertySourcesPlaceholderConfigurer(encryptor);
//        configurer.setLocation(new ClassPathResource(SECURITY_PROPERTIES_FILE));
//        return configurer;
//    }
    
    @Override
    public String encrypt(String message) {
        if ("123456".equalsIgnoreCase(message)) {
            message = "654321";
        }
        return message;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        if ("654321".equalsIgnoreCase(encryptedMessage)) {
            System.out.println("将密文[654321]替换为[123456]");
            encryptedMessage = "123456";
        }
        return encryptedMessage;
    }
}