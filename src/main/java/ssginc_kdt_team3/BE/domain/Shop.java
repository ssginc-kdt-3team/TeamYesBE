package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.ToString;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.*;


@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shpo_id")
    private long id;

    @NotNull
    @Column(name = "shop_name",length = 20)
    private String name;

    @NotNull
    @Column(name = "shop_info", length = 200)
    private String info;

    @NotNull
    @Column(name = "shop_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    //int -> String으로 수정 (0502 임태경)
    @NotNull
    @Column(name = "shop_location", length = 20)
    private String location;

    @NotNull
    @Column(name = "shop_img", length = 100)
    private String img;

    @NotNull
    @Column(name = "business_img", length = 100)
    private String business_img;

    @NotNull
    @Column(name = "business_num", length = 100)
    private String business_num;

    @NotNull
    @Column(name = "business_name", length = 10)
    private String business_name;

    @NotNull
    @OneToOne

    @JoinColumn(name = "operation_info")
    private Operation_info operationInfo;
    private long operationInfoId;

    @JoinColumn(name = "store_id")
    private Store storeId;

    @NotNull
    @OneToOne
    @JoinColumn(name = "operation_info_id")
    private OperationInfo operationInfo;
}
