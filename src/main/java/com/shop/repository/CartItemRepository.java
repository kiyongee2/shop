package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.dto.CartDetailDto;
import com.shop.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	//장바구니 품목 조회(카트 아이디와 상품 아이디를 파라미터로 이용함)
	CartItem findByCartIdAndItemId(Long cartId, Long itemId);
	
	//장바구니 품목 리스트 조회
	/*@Query("SELECT new com.shop.dto.CartDetailDto(ci.id, "
		 + "i.itemNm, i.price, i.count, im.imgUrl) "
		 + "FROM CartItem ci, ItemImg im "
		 + "JOIN ci.item i "
		 + "WHERE ci.cart.id = :cartId "
		 + "AND im.item.id = ci.item.id "
		 + "AND im.repimgYn = 'Y' "
		 + "ORDER BY ci.regTime DESC")
	List<CartDetailDto> findCartDetailDtoList(Long cartId);*/
	
	@Query("SELECT new com.shop.dto.CartDetailDto(ci.id, "
			+ "i.itemNm, i.price, ci.count, im.imgUrl) "
		 + "FROM CartItem ci, ItemImg im "
		 + "JOIN ci.item i "
		 + "WHERE ci.cart.id = :cartId "
		 + "AND im.item.id = ci.item.id "
		 + "AND im.repimgYn = 'Y' "
		 + "ORDER BY ci.regTime DESC"
		)
	List<CartDetailDto> findCartDetailDtoList(Long cartId);
}











