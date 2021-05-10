package com.restaurant.eatenjoy.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.eatenjoy.dao.RestaurantDao;
import com.restaurant.eatenjoy.dto.FileDto;
import com.restaurant.eatenjoy.dto.RestaurantDto;
import com.restaurant.eatenjoy.dto.RestaurantInfo;
import com.restaurant.eatenjoy.dto.RestaurantListDto;
import com.restaurant.eatenjoy.dto.UpdateRestaurant;
import com.restaurant.eatenjoy.exception.BizrNoValidException;
import com.restaurant.eatenjoy.exception.DuplicateValueException;
import com.restaurant.eatenjoy.exception.FileNotSupportException;
import com.restaurant.eatenjoy.exception.FileUploadFailedException;
import com.restaurant.eatenjoy.exception.RestaurantMinOrderPriceValueException;
import com.restaurant.eatenjoy.util.file.FileService;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

	public static final Long OWNER_ID = 1L;

	@Mock
	private RestaurantDao restaurantDao;

	@Mock
	private FileService fileService;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@InjectMocks
	private RestaurantService restaurantService;

	private RestaurantDto duplicatedBizrNoRestaurantDto(FileDto fileDto) {
		RestaurantDto restaurantDto = RestaurantDto.builder()
			.name("청기와")
			.bizrNo("1234567891")
			.telNo("02-123-4567")
			.intrDc("청기와 소개글")
			.paymentType("매장 결제")
			.ownerId(OWNER_ID)
			.categoryId(1L)
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.postCd("13494")
			.baseAddress("경기 성남시 분당구 판교역로 235")
			.detailAddress(null)
			.sigunguCd("41135")
			.uploadFile(fileDto)
			.build();

		return restaurantDto;
	}

	private RestaurantDto paymentTypeRestaurantDto() {
		RestaurantDto restaurantDto = RestaurantDto.builder()
			.name("청기와")
			.bizrNo("1234567891")
			.telNo("02-123-4567")
			.intrDc("청기와 소개글")
			.paymentType("선불")
			.ownerId(OWNER_ID)
			.categoryId(1L)
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.postCd("13494")
			.baseAddress("경기 성남시 분당구 판교역로 235")
			.detailAddress(null)
			.sigunguCd("41135")
			.build();

		return restaurantDto;
	}

	private RestaurantDto notExistBizrNoRestaurntDto() {
		RestaurantDto restaurantDto = RestaurantDto.builder()
			.name("청기와")
			.bizrNo("1234567892")
			.telNo("02-123-4567")
			.intrDc("청기와 소개글")
			.paymentType("매장 결제")
			.ownerId(OWNER_ID)
			.categoryId(1L)
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.postCd("13494")
			.baseAddress("경기 성남시 분당구 판교역로 235")
			.detailAddress(null)
			.sigunguCd("41135")
			.build();

		return restaurantDto;
	}

	private RestaurantDto successRestaurantDto() {
		RestaurantDto restaurantDto = RestaurantDto.builder()
			.name("청기와")
			.bizrNo("1234567891")
			.telNo("02-123-4567")
			.intrDc("청기와 소개글")
			.paymentType("매장 결제")
			.ownerId(OWNER_ID)
			.categoryId(1L)
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.postCd("13494")
			.baseAddress("경기 성남시 분당구 판교역로 235")
			.detailAddress(null)
			.sigunguCd("41135")
			.uploadFile(null)
			.build();

		return restaurantDto;
	}

	private RestaurantInfo generateRestaurantInfo() {
		RestaurantInfo restaurantInfo = RestaurantInfo.builder()
			.id(1L)
			.name("청기와")
			.bizrNo("1234567891")
			.address("수원시")
			.regionCd("cod")
			.telNo("02-123-4567")
			.intrDc("청기와 소개글")
			.minOrderPrice(0)
			.paymentType("매장 결제")
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.build();

		return restaurantInfo;
	}

	private RestaurantListDto createRestaurantData(long id, String name, String intrDc) {
		RestaurantListDto restaurantListDto = RestaurantListDto.builder()
			.id(id)
			.name(name)
			.intrdc(intrDc)
			.build();

		return restaurantListDto;
	}

	private UpdateRestaurant createUpdateRestaurantData() {
		UpdateRestaurant updateRestaurant = UpdateRestaurant.builder()
			.id(1L)
			.name("테스트 식당")
			.bizrNo("1234567891")
			.address("수원시")
			.regionCd("cod")
			.telNo("02-123-4567")
			.intrDc("테스트 식당 수정글")
			.minOrderPrice(0)
			.paymentType("매장 결제")
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.build();

		return updateRestaurant;
	}

	private UpdateRestaurant notExistBizrNoUpdateRestaurantData() {
		UpdateRestaurant updateRestaurant = UpdateRestaurant.builder()
			.id(1L)
			.name("테스트 식당")
			.bizrNo("1234567892")
			.address("수원시")
			.regionCd("cod")
			.telNo("02-123-4567")
			.intrDc("테스트 식당 수정글")
			.minOrderPrice(0)
			.paymentType("매장 결제")
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.build();

		return updateRestaurant;
	}

	private UpdateRestaurant paymentTypeUpdateRestaurant() {
		UpdateRestaurant updateRestaurant = UpdateRestaurant.builder()
			.id(1L)
			.name("테스트 식당")
			.bizrNo("1234567892")
			.address("수원시")
			.regionCd("cod")
			.telNo("02-123-4567")
			.intrDc("테스트 식당 수정글")
			.minOrderPrice(0)
			.paymentType("선불")
			.openTime(LocalTime.of(9, 00))
			.closeTime(LocalTime.of(23, 00))
			.build();

		return updateRestaurant;
	}

	private MockMultipartFile getMockMultipartFile(String name, String originalFilename) {
		return new MockMultipartFile(name, originalFilename, null, name.getBytes());
	}

	@Test
	@DisplayName("식당 등록 성공 - 이미지 파일을 올리지 않고 식당 등록을 성공한다")
	void successToRegisterRestaurantNoImageFile() {
		restaurantService.register(successRestaurantDto(), OWNER_ID);
		then(restaurantDao).should(times(1)).register(any(RestaurantDto.class));
	}

	@Test
	@DisplayName("식당 이미지 파일 업로드에 성공한다")
	void successToUploadFile() {
		// given
		MultipartFile photo = getMockMultipartFile("test", "test.jpg");

		FileDto fileDto = FileDto.builder()
			.id(1L)
			.build();

		given(fileService.uploadFile(photo)).willReturn(fileDto);
		given(fileService.saveFileInfo(fileDto)).willReturn(fileDto.getId());

		// when
		restaurantService.uploadImage(photo);

		// then
		then(fileService).should(times(1)).uploadFile(photo);
		then(fileService).should(times(1)).saveFileInfo(fileDto);
	}

	@Test
	@DisplayName("식당 등록 실패 - 매장 결제 방식이 선불인 경우 최소 주문 가격이 0원이 된다면 식당 등록을 실패한다")
	void failToMinOrderPriceByPaymentType() {
		assertThrows(RestaurantMinOrderPriceValueException.class, () -> {
			restaurantService.register(paymentTypeRestaurantDto(), OWNER_ID);
		});
	}

	@Test
	@DisplayName("식당 등록 실패 - 유효하지 않은 사업자 등록 번호를 입력하면 식당 등록을 실패한다")
	void failToRegisterRestaurantByNotExistsBizrNo() {
		assertThrows(BizrNoValidException.class, () -> {
			restaurantService.register(notExistBizrNoRestaurntDto(), OWNER_ID);
		});
	}

	@Test
	@DisplayName("식당 등록 실패 - 지원하지 않는 확장자를 사용하면 파일 업로드에 실패한다")
	void failByNotSupportedFileExtension() {
		// given
		MultipartFile photo = getMockMultipartFile("test", "test.txt");

		// when
		assertThatThrownBy(() -> restaurantService.uploadImage(photo)).isInstanceOf(FileNotSupportException.class);
	}

	@Test
	@DisplayName("식당 등록 실패 - 중복된 사업자 번호로 식당 등록을 할 경우 식당 등록에 실패하고 업로드된 파일은 삭제한다")
	void failByExistBizrNoAndDeleteUploadFile() {

		FileDto fileDto = FileDto.builder()
			.id(1L)
			.build();

		RestaurantDto dto = duplicatedBizrNoRestaurantDto(fileDto);

		doThrow(DuplicateKeyException.class).when(restaurantDao).register(any());

		assertThatThrownBy(() -> restaurantService.register(dto, OWNER_ID))
			.isInstanceOf(DuplicateValueException.class);

		then(restaurantDao).should(times(1)).register(any());
		then(eventPublisher).should(times(1)).publishEvent(dto.getUploadFile());
	}

	@Test
	@DisplayName("식당 목록 데이터가 있는 경우 리스트를 반환한다")
	void getExistRestaurntList() {
		// given
		List<RestaurantListDto> existRestaurantList = new ArrayList<>();
		existRestaurantList.add(createRestaurantData(1L, "김밥나라", "김밥나라 소개글"));
		existRestaurantList.add(createRestaurantData(2L, "맥도날드", "맥도날드 소개글"));

		when(restaurantDao.findAllRestaurantList(0L, OWNER_ID)).thenReturn(existRestaurantList);

		// when
		List<RestaurantListDto> result = restaurantService.getListOfRestaurant(0L, OWNER_ID);

		// then
		assertEquals(existRestaurantList, result);
	}

	@Test
	@DisplayName("식당 리스트 페이징")
	void getRestaurantListPaging() {
		// given
		List<RestaurantListDto> pagingRestaurList = new ArrayList<>();
		pagingRestaurList.add(createRestaurantData(1L, "롯데리아", "맥도날드 소개글"));

		when(restaurantDao.findAllRestaurantList(2L, OWNER_ID)).thenReturn(pagingRestaurList);

		// when
		List<RestaurantListDto> actual = restaurantService.getListOfRestaurant(2L, OWNER_ID);

		// then
		assertEquals(pagingRestaurList, actual);
	}

	@Test
	@DisplayName("식당 목록 데이터가 없는 경우 비어있는 리스트를 리턴한다")
	void getEmptyRestaurantList() {
		// given
		List<RestaurantListDto> emptyRestaurantList = new ArrayList<>();

		when(restaurantDao.findAllRestaurantList(0L, OWNER_ID)).thenReturn(emptyRestaurantList);

		// when
		List<RestaurantListDto> result = restaurantService.getListOfRestaurant(0L, OWNER_ID);

		// then
		assertEquals(emptyRestaurantList, result);
	}

	@Test
	@DisplayName("식당 데이터 수정 성공")
	void successModifyRestaurant() {
		// given
		UpdateRestaurant restaurant = createUpdateRestaurantData();

		// when
		restaurantService.updateRestaurant(restaurant);

		// then
		then(restaurantDao).should(times(1)).modifyRestaurantInfo(restaurant);
	}

	@Test
	@DisplayName("식당 데이터 수정 실패 - 유효하지 않은 사업자 번호")
	void failModifyRestaurantByBizrNo() {
		assertThrows(BizrNoValidException.class, () -> {
			restaurantService.updateRestaurant(notExistBizrNoUpdateRestaurantData());
		});
	}

	@Test
	@DisplayName("식당 데이터 수정 실패 - RestaurantMinOrderPriceValueException 발생")
	void failModifyRestaurantByPaymentTypeAndMinOrderPrice() {
		assertThrows(RestaurantMinOrderPriceValueException.class, () -> {
			restaurantService.updateRestaurant(paymentTypeUpdateRestaurant());
		});
	}

	@Test
	@DisplayName("식당 데이터 수정 실패 - 이미 존재하는 사업자 번호")
	void failModifyRestaurantByExistBizrNo() {
		doThrow(DuplicateKeyException.class).when(restaurantDao).modifyRestaurantInfo(any());

		assertThatThrownBy(() -> restaurantService.updateRestaurant(createUpdateRestaurantData()))
			.isInstanceOf(DuplicateValueException.class);

		then(restaurantDao).should(times(1)).modifyRestaurantInfo(any());
	}
}
