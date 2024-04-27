package au.edu.utas.kaimol.cricketscore.view

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.adapter.PlayerContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import au.edu.utas.kaimol.cricketscore.validator.EmptySetupValidator
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BattingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBattingTeamSetupBinding
    private lateinit var getPermissionResult: ActivityResultLauncher<String>
    private lateinit var targetImageView: ImageView
    private lateinit var currentPhotoPath: String
    private var photoPath1: String? = null
    private var photoPath2: String? = null
    private var photoPath3: String? = null
    private var photoPath4: String? = null
    private var photoPath5: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityBattingTeamSetupBinding.inflate(layoutInflater)
        setContentView(ui.root)
        currentPhotoPath = ""
        ui.img1.setImageResource(R.drawable.avatar0)
        ui.img2.setImageResource(R.drawable.avatar0)
        ui.img3.setImageResource(R.drawable.avatar0)
        ui.img4.setImageResource(R.drawable.avatar0)
        ui.img5.setImageResource(R.drawable.avatar0)

        getPermissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){
                result: Boolean ->
            if(result){
                // Permission is granted
                takePicture()
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }

        val teamSetupController = TeamSetupController()
        val teamSetupValidator = EmptySetupValidator()

        ui.battersInfoList.adapter = PlayerContainerAdapter(playerIndexes)
        ui.battersInfoList.layoutManager = LinearLayoutManager(this)

        ui.img1.setOnClickListener {
            targetImageView = ui.img1
            requestToTakePicture()
            photoPath1 = currentPhotoPath
        }

        ui.img2.setOnClickListener {
            targetImageView = ui.img2
            requestToTakePicture()
            photoPath2 = currentPhotoPath
        }

        ui.img3.setOnClickListener {
            targetImageView = ui.img3
            requestToTakePicture()
            photoPath3 = currentPhotoPath
        }

        ui.img4.setOnClickListener {
            targetImageView = ui.img4
            requestToTakePicture()
            photoPath4 = currentPhotoPath
        }

        ui.img5.setOnClickListener {
            targetImageView = ui.img5
            requestToTakePicture()
            photoPath5 = currentPhotoPath
        }
        ui.btnNext.setOnClickListener {
            if(teamSetupValidator.teamSetupValidation(ui)){
                teamSetupController.createValidationToast(
                    this,
                    "Please fill all the fields.",
                )
            } else {
                val teamName = ui.txtBattingTeamName.text.toString()
                val team = Team(name = teamName, teamType = TeamType.BATTING)

                teamSetupController.saveTeam(team)
                val batters = teamSetupController.getBattersFromView(ui)

                //save Batters into Firebase
                teamSetupController.savePlayers(batters, team)
                val i = Intent(this, BowlingTeamSetup::class.java)
                i.putExtra("battingTeamName", team.name)
                for (index in 0 until batters.size){
                    i.putExtra("batter${index + 1}", batters[index].name)
                }
                i.putExtra("batterPhoto1", photoPath1)
                i.putExtra("batterPhoto2", photoPath2)
                i.putExtra("batterPhoto3", photoPath3)
                i.putExtra("batterPhoto4", photoPath4)
                i.putExtra("batterPhoto5", photoPath5)
                startActivity(i)
            }
        }
        ui.btnCancel.setOnClickListener {
            finish()
        }
    }
    private fun requestToTakePicture(){
        getPermissionResult.launch(Manifest.permission.CAMERA)
    }

    private fun takePicture(){
        val photoFile: File = createImageFile()
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "au.edu.utas.kaimol.cricketscore.provider",
            photoFile
        )
        getCameraResult.launch(photoURI)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
    private val getCameraResult = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            result: Boolean ->
        if(result){
            when(targetImageView){
                ui.img1 -> photoPath1 = currentPhotoPath
                ui.img2 -> photoPath2 = currentPhotoPath
                ui.img3 -> photoPath3 = currentPhotoPath
                ui.img4 -> photoPath4 = currentPhotoPath
                ui.img5 -> photoPath5 = currentPhotoPath
            }
            setPic(targetImageView)
        }
    }
    private lateinit var currentImageView: ImageView
    private fun setPic(imageView: ImageView){
        currentImageView = imageView
        val targetW: Int = imageView.width
        val targetH: Int = imageView.height
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(currentPhotoPath, this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight
            val scaleFactor: Int = Math.min(photoW/targetW, photoH/targetH)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        imageView.setImageBitmap(bitmap)
    }
}