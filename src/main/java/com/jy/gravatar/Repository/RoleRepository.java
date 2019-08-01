package com.jy.gravatar.Repository;

import com.jy.gravatar.Beans.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
