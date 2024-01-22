package com.wewrite.android.ui.onboarding

import com.wewrite.android.R

data class OnBoardingData(
    val image: Int,
    val title: String,
    val content: String
)

val onBoardingList = listOf(
    OnBoardingData(
        image = R.drawable.img_onboarding1,
        title = "소중한 추억들을\n 지도에 기록해 보아요!",
        content = "내가 찍은 사진과 그날의\n 기록을 지도에서 한눈에 확인해요!"
    ),
    OnBoardingData(
        image = R.drawable.img_onboarding2,
        title = "우리들만의 추억을\n지도에서 한눈에 확인해요!",
        content = "원하는 친구에게만 추억을 공유하고\n기록해 보세요"
    ),
    OnBoardingData(
        image = R.drawable.img_onboarding3,
        title = "특별했던 순간을\n간편하게 확인하고 싶나요?",
        content = "특별했던 나, 우리의 기록을 스크랩하고\n간편하게 관리해 보세요"
    )
)
