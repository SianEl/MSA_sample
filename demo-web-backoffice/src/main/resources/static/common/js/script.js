$(document).ready(function () {
  const winH = $(window).outerHeight();
  const conH = winH - 42; // 42: header height

  $('.popup-content').css('height', conH);
  tabControl();
  passengerPopupCall();
  calendarPopupCall();
  citysearchPopupCall();
  detailFlight();
  // likeSearchPopupCall();
}); // 항공권 상세 보기

function detailFlight() {
  $('.js-detailFlight').bind('click', function () {
    $('.js-detail').css('display', 'flex');
  });
  popupCloser();
} // 바닦 페이지 고정


function closeBackground() {
  $('body').css('position', 'fixed');
} // 팝업닫기


function popupCloser() {
  $('.js-popupClose').bind('click', function () {
    $('.popup').css('display', 'none');
    $('body').css('position', 'relative');
  });
} // 탑승인원 선택 팝업


function passengerPopupCall() {
  $('.js-passengerCall').bind('click', function () {
    $('.js-passenger').css('display', 'flex');
    closeBackground();
  });
  popupCloser();
} // 날짜 선택 팝업


function calendarPopupCall() {
  $('.js-calendarCall').bind('click', function () {
    $('.js-calendar').css('display', 'flex');
    let searchType = $('.search-tab div.active input[name="search"]').val();
    if(searchType == "likeSearch") {
      $('.info-box .item span').eq(0).text('여행 출발일');
      $('.info-box .item span').eq(1).text('여행 종료일');
    } else {
      $('.info-box .item span').eq(0).text('가는 날짜');
      $('.info-box .item span').eq(1).text('오는 날짜');
    }
    closeBackground();
  });
  popupCloser();
} // 도시 선택 팝업


function citysearchPopupCall() {
  $('.js-citysearchCall').bind('click', function () {
    $('.js-citysearch').css('display', 'flex');
    $('.search-box').focus();
    let searchType = $('.search-tab div.active input[name="search"]').val();
    if(searchType == "likeSearch") {
      $('.popup-title').eq(2).text('지역 검색')
      $('#citySearch').attr('placeholder', '지역을 입력하세요')
    } else {
      $('.popup-title').eq(2).text('도시 검색')
      $('#citySearch').attr('placeholder', '도시명을 입력하세요')
    }
    closeBackground();
  });
  popupCloser();
} // 항공편 선택

// function likeSearchPopupCall() {
//   $('.js-likeSearchCall').bind('click', function () {
//     $('.js-likeSearch').css('display', 'flex');
//     $('.search-box').focus();
//     closeBackground();
//   });
//   popupCloser();
// }

function tabControl() {
  $('.tab-content').hide();
  $('.search-tab .tab:first').addClass('active').show().find('label input:radio').attr('checked', '');
  $('.tab-content:first').show();
  $('.search-tab .tab').click(function () {
    $('.search-tab .tab').removeClass('active');
    $('.search-tab .tab').find('label input:radio').attr('checked', '');
    $(this).addClass('active').find('label input:radio').attr('checked', 'checked');
    $('.tab-content').hide();
    let activeTab = $(this).find('label input:radio').val();
    $('#' + activeTab).show();
    return false;
  });
}
