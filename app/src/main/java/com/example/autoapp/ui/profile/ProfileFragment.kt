package com.example.autoapp.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.autoapp.MainActivity
import com.example.autoapp.R
import com.example.autoapp.api.api_resource
import com.example.autoapp.auth.Login
import com.example.autoapp.databinding.FragmentProfileBinding
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as? MainActivity)?.act_bar()
        val carBlocksContainer = binding.carBlocksContainer
        val button = binding.buttonExit
        val text_name = binding.textNameBlock

        val progressBar = binding.progressBar4
        progressBar.visibility = View.VISIBLE
        val sharedPreference = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val user_id = sharedPreference.getString("user_id", "")?.toIntOrNull()
        val user_name = sharedPreference.getString("user_name", "")

        button.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove("user_id")
            editor.remove("user_name")
            editor.putString("login", "false")
            editor.apply()

            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
        }

        text_name.text = "Логин - ${user_name}"

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = api_resource()
                val result = data.get_all_saved_cars(user_id)
                if (result.isNotEmpty()) {
                    //вызов функции отрисовки блоков
                    val title = TextView(requireContext())
                    title.text = "Все добавленные машины"
                    title.setPadding(100,100,100,100)
                    title.gravity = Gravity.CENTER
                    title.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    title.textSize = 30F

                    carBlocksContainer.addView(title)

                    for (car_inf in result) {

                        val imageView = RoundedImageView(requireContext())
                        Glide.with(requireContext())
                            .load(car_inf.image_url)
                            .into(imageView)

                        val block = create_block_ser(
                            car_inf.pk,
                            imageView,
                            car_inf.brand,
                            car_inf.model,
                            car_inf.mileage,
                            car_inf.condition,
                            car_inf.owners_count,
                            car_inf.car_type,
                            car_inf.salon.name,
                            car_inf.salon.address,
                            car_inf.price
                        )
                        carBlocksContainer.addView(block)
                        carBlocksContainer.gravity = Gravity.CENTER
                    }

                } else {
                    // Обработка случая, когда список пуст
                    Log.e("BusActivity", "Response failed - result is empty")

                    //val error = createBusEpty()
                    //BusesContainer.addView(error)
                }
            } catch (e: Exception) {
                // Ловим и обрабатываем исключения, например, связанные с сетевыми ошибками
                Log.e("BusActivity", "Error during response", e)
                e.printStackTrace()
            }
            progressBar.visibility = View.GONE
        }

        return root
    }

    private fun create_block_ser(id: Int, image: RoundedImageView, brand: String, model: String, mileage: Int, condition: String, owners_count: Int, car_type:String, name: String, address: String, price: Int): LinearLayout {
        //общий блок
        val block = LinearLayout(requireContext())

        val blockParams = LinearLayout.LayoutParams(
            1000,
            1700
        )
        blockParams.setMargins(0, 0, 10, 0)
        blockParams.bottomMargin = 100
        block.layoutParams = blockParams
        //block.gravity = Gravity.CENTER
        block.orientation = LinearLayout.VERTICAL
        val backgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_background)
        block.background = backgroundDrawable

        //image
        val imageLayputParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            500
        )

        //imageLayputParams.setMargins(20, 20, 20, 20)
        image.cornerRadius = 20F
        image.layoutParams = imageLayputParams
        image.scaleType = ImageView.ScaleType.CENTER_CROP

        //название машины
        val brand_text = TextView(requireContext())
        brand_text.text = "${brand} $model"
        brand_text.setTextAppearance(R.style.Title_style)

        brand_text.setPadding(30,50,30,0)
        brand_text.gravity = Gravity.LEFT

        //блок информация
        val block_inf = LinearLayout(requireContext())
        val block_inf_params = LinearLayout.LayoutParams(
            900,
            1000
        )
        block_inf_params.setMargins(50, 0, 0, 0)
        block_inf_params.bottomMargin = 10
        block_inf.layoutParams = block_inf_params
        //block.gravity = Gravity.CENTER
        block_inf.orientation = LinearLayout.VERTICAL

        //пробег

        val format = NumberFormat.getNumberInstance(Locale.getDefault())
        val form_mileage = format.format(mileage)

        val mileage_text = TextView(requireContext())
        mileage_text.text = Html.fromHtml("&#8226; <b>Пробег:</b> ${form_mileage} км", Html.FROM_HTML_MODE_COMPACT)
        mileage_text.setTextAppearance(R.style.Text_style)

        mileage_text.setPadding(30,20,30,0)
        mileage_text.gravity = Gravity.LEFT

        //состояние
        val cindition_text = TextView(requireContext())
        cindition_text.text = Html.fromHtml("&#8226; <b>Состояние машины:</b> ${condition}", Html.FROM_HTML_MODE_COMPACT)
        cindition_text.setTextAppearance(R.style.Text_style)

        cindition_text.setPadding(30,20,30,0)
        cindition_text.gravity = Gravity.LEFT

        //владельцы
        val owners_text = TextView(requireContext())
        owners_text.text = Html.fromHtml("&#8226; <b>Количество владельцев:</b> ${owners_count}", Html.FROM_HTML_MODE_COMPACT)
        owners_text.setTextAppearance(R.style.Text_style)

        owners_text.setPadding(30,20,30,0)
        owners_text.gravity = Gravity.LEFT

        //тип машины
        val car_type_text = TextView(requireContext())
        car_type_text.text = Html.fromHtml("&#8226; <b>Тип машины:</b> ${car_type}", Html.FROM_HTML_MODE_COMPACT)
        car_type_text.setTextAppearance(R.style.Text_style)

        car_type_text.setPadding(30,20,30,0)
        car_type_text.gravity = Gravity.LEFT

        //название салона
        val salon_name = TextView(requireContext())
        salon_name.text = Html.fromHtml("${name}", Html.FROM_HTML_MODE_COMPACT)
        salon_name.setTextAppearance(R.style.Title_style)

        salon_name.setPadding(0,50,50,0)
        salon_name.gravity = Gravity.LEFT

        //название салона
        val salon_address = TextView(requireContext())
        salon_address.text = Html.fromHtml("Адрес -  ${address}", Html.FROM_HTML_MODE_COMPACT)
        salon_address.setTextAppearance(R.style.Text_address)

        salon_address.setPadding(50,20,50,0)
        salon_address.gravity = Gravity.LEFT

        //цена
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val form_price = formatter.format(price)

        val price_text = TextView(requireContext())
        price_text.text = Html.fromHtml("Цена - ${form_price} руб", Html.FROM_HTML_MODE_COMPACT)
        price_text.setTextAppearance(R.style.Title_style)

        price_text.setPadding(0,50,50,0)
        price_text.gravity = Gravity.LEFT


        block.addView(image)
        block.addView(brand_text)

        block_inf.addView(mileage_text)
        block_inf.addView(cindition_text)
        block_inf.addView(owners_text)
        block_inf.addView(car_type_text)
        block_inf.addView(salon_name)
        block_inf.addView(salon_address)
        block_inf.addView(price_text)

        block.addView(block_inf)

        return block

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}