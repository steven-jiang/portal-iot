package com.kii.beehive.portal.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.kii.beehive.portal.store.entity.TagThingIndex;

@Component
public interface TagRepository extends CrudRepository<TagThingIndex,String> {
}
