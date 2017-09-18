package demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import demo.controller.CorrelationIdAdvice;





@Configuration
@EnableCaching
//public class SARestClientConfig /*extends WebSecurityConfigurerAdapter*/{
	public class SARestClientConfig	extends WebMvcConfigurerAdapter {

	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new CorrelationIdAdvice());
	    }

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**");
	    }
	@Bean
	public RestTemplate createRestTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public CacheManager cacheManager() {
	   return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
	   EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
	   cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
	   cmfb.setShared(true);

	   return cmfb;
	}

	/*@Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }*/
	/*@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}*/
	
	/*@Bean
	private static DataSource dataSource(){
		SQLServerConnectionPoolDataSource dataSource = new SQLServerConnectionPoolDataSource();
		dataSource.setServerName("USHYDSASAHAY1");
		dataSource.setDatabaseName("Persons");
		dataSource.setSendStringParametersAsUnicode(false);
		dataSource.setIntegratedSecurity(true);
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}*/
	
	
   
  
/*   
// Authentication : User --> Roles
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password("secret1")
				.roles("USER").and().withUser("admin1").password("secret1")
				.roles("USER", "ADMIN");
	}

	// Authorization : Role -> Access
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/welcome/**")
				.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
				.csrf().disable().headers().frameOptions().disable();
	}*/
   
}


 
 
 


