package cn.hollis.nft.turbo.collection.domain.request;

import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import lombok.*;



@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeldCollectionTransferRequest extends BaseHeldCollectionRequest {

    //买家id
    private String recipientUserId;

    //操作人Id
    private String operatorId;

    @Override
    public HeldCollectionEventType getEventType() {
        return HeldCollectionEventType.TRANSFER;
    }
}
