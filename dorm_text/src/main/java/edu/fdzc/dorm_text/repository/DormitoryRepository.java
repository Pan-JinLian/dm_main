package sut.edu.zyp.dormitory.manage.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sut.edu.zyp.dormitory.manage.entity.DormitoryEntity;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "dormitory", path = "dormitory")
public interface DormitoryRepository extends PagingAndSortingRepository<DormitoryEntity, String> {

    /**
     * 按照宿舍编号查询宿舍信息
     *
     * @param id 宿舍编号
     * @return 宿舍信息
     */
    Optional<DormitoryEntity> findById(String id);//修改部分
}
