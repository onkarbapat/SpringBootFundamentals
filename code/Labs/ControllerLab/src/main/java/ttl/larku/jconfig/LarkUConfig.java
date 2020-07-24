package ttl.larku.jconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.inmemory.InMemoryTrackDAO;
import ttl.larku.dao.jpahibernate.JPATrackDAO;
import ttl.larku.domain.Track;
import ttl.larku.service.TrackService;

import java.util.List;

@Configuration
@ComponentScan({"ttl.larku.service", "ttl.larku.dao"})
@PropertySource({"classpath:/larkUContext.properties"})
public class LarkUConfig {

    @Autowired
    private Environment env;

    private LarkUTestDataConfig testDataProducer = new LarkUTestDataConfig();

    @Bean
    @Profile("development")
    public BaseDAO<Track> trackDAO() {
        return testDataProducer.trackDAOWithInitData();
    }

    @Bean(name = "trackDAO")
    @Profile("production")
    public BaseDAO<Track> trackDAOJpa() {
        return jpaTrackDAOWithTestData();
    }


    @Bean
    public BaseDAO<Track> inMemoryTrackDAO() {
        return new InMemoryTrackDAO();
    }

    @Bean
    public BaseDAO<Track> jpaTrackDAOWithTestData() {
        JPATrackDAO dao = new JPATrackDAO();
        List<Track> fakeTracks = testDataProducer.tracks();
        fakeTracks.forEach(dao::create);

        return dao;
    }

    @Bean
    public BaseDAO<Track> jpaTrackDAO() {
        JPATrackDAO dao = new JPATrackDAO();
        return dao;
    }

    @Bean
    public TrackService trackService() {
        TrackService ss = new TrackService();
        ss.setTrackDAO(trackDAO());

        return ss;
    }
}