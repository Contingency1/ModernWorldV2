package kr.modernworld.modernworldv2.admin.domain.item.port;

import kr.modernworld.modernworldv2.admin.application.item.api.ItemNameAndPriceDTO;

public interface ItemApi {

  ItemNameAndPriceDTO getNameAndPrice(Long itemNo);

}
