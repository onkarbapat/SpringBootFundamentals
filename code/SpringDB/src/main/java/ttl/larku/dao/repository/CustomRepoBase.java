package ttl.larku.dao.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * An interface for a custom Repository.  We can use this
 * to completely specify our repository interface
 *
 * Only the methods in this interface, and any others specified
 * in the extending interface will be part of the repository.
 *
 * The interface *has* to be annotated with @NoRepositoryBean
 * and it *has* to be extended by some other interface which will
 * be the one that clients will use.
 *
 * @param <T>
 * @param <ID>
 * @author whynot
 */
@NoRepositoryBean
public interface CustomRepoBase<T, ID extends Serializable> extends Repository<T, ID> {

    //Can either return an Optional
    Optional<T> findById(@Param("id") ID id);

    //Or a null
    @Nullable
    T findNullableById(@Param("id") ID id);

    //Or get an EmptyResultDataAccessException
    T findExceptionById(@Param("id") ID id);

    List<T> findAll();
}
