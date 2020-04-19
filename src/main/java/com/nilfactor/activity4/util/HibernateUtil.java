package com.nilfactor.activity4.util;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Dependent
@Named
public class HibernateUtil {
	private SessionFactory sessionFactory = buildSessionFactory();
	
	public HibernateUtil() {
		
	}
	
    private SessionFactory buildSessionFactory() {
    	try
		{
			if (sessionFactory == null)
			{
				Configuration configuration = new Configuration().configure();
				configuration.addResource("User.hbm.xml");
				configuration.addResource("SpotifySong.hbm.xml");
				configuration.addResource("SpotifyAlbum.hbm.xml");
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				return configuration.buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }
}