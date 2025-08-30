package banek.stef.domain.cookies.api

import banek.stef.domain.cookies.api.model.CookieDto
import banek.stef.domain.cookies.api.model.FavoriteCookieRequest
import kotlinx.coroutines.delay

internal class CookieApiImpl : CookieApi {

    override suspend fun fetchCookies(): Result<List<CookieDto>> {
        return Result.success(COOKIES)
    }

    override suspend fun favoriteCookie(request: FavoriteCookieRequest): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    companion object {
        private val COOKIES = listOf(
            CookieDto(
                id = "1",
                name = "Chocolate Chip",
                description = "A delicious cookie with chocolate chips.",
                imageUrl = "https://thedefineddish.com/wp-content/uploads/2022/07/Perfect-Paleo-Chocolate-Chip-Cookies-4-scaled.jpg"
            ),
            CookieDto(
                id = "2",
                name = "Oatmeal Raisin",
                description = "A healthy cookie with oats and raisins.",
                imageUrl = "https://www.cookingclassy.com/wp-content/uploads/2014/07/one-oatmeal-raisin-cookie+srgb..jpg"
            ),
            CookieDto(
                id = "3",
                name = "Fortune cookie",
                description = "A cookie that pronounces your doom. Or not.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d2/Fortune_cookies.jpg"
            ),
            CookieDto(
                id = "4",
                name = "Gingerbread man",
                description = "A very sweet cookie and possibly a friend that wishes you Merry Christmas. But then you eat him.",
                imageUrl = "https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcS2_LIzBj0VcW1pczzRN8ewFZAMDuW9AwZ6RoPKX6YFCNWVW40UFzJZcIr799hFeor0hAU_kCzP3ZSDU-s4eQ8eR4_9Rh-TmDWqNim5i9VV5fgdge4"
            ),
            CookieDto(
                id = "5",
                name = "Macarons",
                description = "I don't really like those, but they look nice.",
                imageUrl = "https://cakesbymk.com/wp-content/uploads/2024/02/Template-Size-for-Blog-Photos-64.jpg"
            ),
            CookieDto(
                id = "6",
                name = "Grandma's special plate",
                description = "My grandma's continuous efforts to give me diabetes.",
                imageUrl = "https://www.marcellinaincucina.com/wp-content/uploads/2023/08/italian-butter-cookies-hero.jpg"
            )
        )
    }
}