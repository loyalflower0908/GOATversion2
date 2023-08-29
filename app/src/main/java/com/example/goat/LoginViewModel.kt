package com.example.goat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // 로그인 결과 반환 변수
    private val _loginResult = MutableSharedFlow<Boolean>()
    //var loginResult = _loginResult.asSharedFlow()

    fun tryLogin(context: Context) {
        viewModelScope.launch {
            val account = async {
                getLastSignedInAccount(context)
            }
            delay(2500)
            // 계정 확인 -> true, 없음 -> false 반환
            setLoginResult(account.await() != null)
        }
    }

    // 이전에 로그인 한 계정이 있는지 확인
    private fun getLastSignedInAccount(context: Context) = GoogleSignIn.getLastSignedInAccount(context)

    private fun setLoginResult(isLogin: Boolean) {
        viewModelScope.launch {
            _loginResult.emit(isLogin)
        }
    }
}
