package com.example.dibujosdeldia.ui.main.api

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.R

//блок прятки клавиатуры
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

val spannableEarthDescription = SpannableString("Спутник пролетает над каждой точкой своего популярного маршрута раз в 16 суток. Фотографий из удалённых мест земного шара в базе НАСА может не быть.\n \nОблака могут быть помехой. \nШироты южного полушария и западные долготы считаются отрицательными. \n\nВы можете попробовать ввести следующие координаты: \n широта - долгота \nМосква 55.45 37.37 \nМадрид 40.24 -3.41 \nЛондон 51.30 -00.07 \nРим 45.54 12.3 \nПекин 39.54 116.23 \nСингапур 1.29 103.85 \nСидней -33.31 151.12 \nЛос-Анджелес 34.02 -118.16 \nВашингтон 38.53 -77.02 \nБуэнос-Айрес -34.35 -58.22 \nБразилиа -15.47 -47.52 \nСантьяго -33.27 -70.40 \nточка в лесу Австралии -33.52 151.12 \nточка в пустыне Атакама -27.37 -70.31 \nточка в пустыне Сахара 30.44 7.76 \nточка в Атлантическом океане 33.27 -8.56 \nточка в Тихом океане -24.03 -71.32 \nточка в Ледовитом океане 81.36 31.56 \nточка в Антарктике -78.3 8.02 \nвулкан Тейде 28.27 -16.64 \nвулкан Кракатау -6.1 105.42 \nГалапагосские острова -0.62 -90.45 \nАризонский кратер 35.01 -111.01 \nкратер Вредефорт -27 27.3")
val spannableMarsDescription = SpannableString("Марсоход \"Кьюриосити\" был запущен с мыса Канаверал 26 ноября 2011 года и приземлился на Марсе в кратере Гейла 6 августа 2012 года.\n\nКратер Гейла имеет диаметр 154км и, возможно, заполнен эродированными горными породами, озёрными отложенями.\n\nНа 15 августа 2021 года марсоход преодолел более 26км. Он оснащён радиоизотопной системой питания, которая вырабатывает электричество из тепла радиоактивного распада плутония. Средняя скорость марсохода 30м/ч.\n\nНа марсоходе установлено несколько камер. Вы можете получать фотографии со следующих:\nFHAZ - передняя камера, ч/б фотографии\nRHAZ - задняя камера, ч/б фотографии\nMAST - мачтовая камера, ч/б и цветные фотографии\nCHEMCAM - \"геохимическая\" камера, лазер, камера, спектрограф, ч/б\nNAVCAM - навигационная камера, ч/б\nMAHLI - \"геологическая\" камера для детальных снимков поверхности, цветная\n\nВНИМАНИЕ! Не всеми камерами \"Кьюриосити\" пользовался каждый сол. В какой-то сол фотографий с запрошенной камеры может не быть.\n\nПродолжительность сола - марсианских суток - 24 ч 39 мин. Марсианский год длится 669 солов или 687 земных суток.\n\nДорогой Марс, с нетерпением ждём терраформирования и колонизации.")