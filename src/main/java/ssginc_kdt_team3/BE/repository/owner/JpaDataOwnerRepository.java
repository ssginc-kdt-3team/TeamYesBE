package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.DTOs.admin.AdminBranchOwnerDTO;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import java.util.List;
import java.util.Optional;
@Repository
public interface JpaDataOwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(String email);

//    @Query("SELECT o.password FROM Owner o WHERE o.email = :email")
//    String PasswordMatchEmail(@Param("email")String email);


    @Query("UPDATE Owner o SET " +
            "o.name = CASE WHEN :name IS NOT NULL THEN :name ELSE o.name END, " +
            "o.phoneNumber = CASE WHEN :phone IS NOT NULL THEN :phone ELSE o.phoneNumber END, " +
            "o.address.city = CASE WHEN :city IS NOT NULL THEN :city ELSE o.address.city END, " +
            "o.address.district = CASE WHEN :district IS NOT NULL THEN :district ELSE o.address.district END, " +
            "o.address.detail = CASE WHEN :detail IS NOT NULL THEN :detail ELSE o.address.detail END, " +
            "o.address.zipCode = CASE WHEN :zipCode IS NOT NULL THEN :zipCode ELSE o.address.zipCode END "+
            "WHERE o.id = :id")
    @Modifying
    void updateByOwner(@Param("id") Long id, @Param("name") String name, @Param("phone")String phone,
                        @Param("city")String city, @Param("district")String district,@Param("detail")String detail,@Param("zipCode")String zipCode);
    //점주 & 관리자가 있었는데 제외됨 : 점주 정보 수정 - 고신영 0524
    boolean existsByid(Long id);

    Optional<Owner> findByEmail(String email);

    Page<Owner> findAllBy(Pageable pageable);

    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.admin.AdminBranchOwnerDTO" +
            "(o.id, o.name, o.phoneNumber, s.name, s.location, b.name) FROM Branch b " +
            "LEFT JOIN Shop s ON b.id = s.branch.id " +
            "LEFT JOIN Owner o ON s.owner.id = o.id " +
            "WHERE b.id = :id")
    Page<AdminBranchOwnerDTO> BranchIdFindShopWithOwner(@Param("id") Long id,Pageable pageable);
    //관리자  : 지점별 매장조회 - 고신영 0524
    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO(o.id, o.email, o.name, o.gender, o.address, o.phoneNumber, o.birthday, o.status, " +
            "s.name, s.location, " +
            "b.name) FROM Owner o " +
            "LEFT JOIN Shop s ON o.id = s.owner.id " +
            "LEFT JOIN Branch b ON s.branch.id = b.id " +
            "WHERE o.id = :id")
    Optional<AdminOwnerDetailDTO> OwnerIdFindByOwnerDetail(@Param("id") Long id);
    //관리자 : 점주 상세 정보조회 수정함 - 고신영 0524

    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO(b.id, s.name, s.location, s.shopImg,s.status) " +
            "FROM Branch b " +
            "LEFT JOIN Shop s ON b.id = s.branch.id " +
            "WHERE b.id = :id")
    List<BranchShopDTO> BranchIdByShop(@Param("id")Long id);
    //고객 : 지점별 매장조회 - 고신영 0524


}

