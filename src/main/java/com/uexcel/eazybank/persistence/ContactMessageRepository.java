package com.uexcel.eazybank.persistence;

import com.uexcel.eazybank.model.ContactMessages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends CrudRepository<ContactMessages,Long> {
}
