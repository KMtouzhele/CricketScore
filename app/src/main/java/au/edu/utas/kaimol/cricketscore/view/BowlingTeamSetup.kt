package au.edu.utas.kaimol.cricketscore.view

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.PlayerContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBowlingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import au.edu.utas.kaimol.cricketscore.validator.EmptySetupValidator
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BowlingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBowlingTeamSetupBinding
    private lateinit var getPermissionResult: ActivityResultLauncher<String>
    private lateinit var targetImageView: ImageView
    private lateinit var currentPhotoPath: String
    private var photoPath6: String? = null
    private var photoPath7: String? = null
    private var photoPath8: String? = null
    private var photoPath9: String? = null
    private var photoPath10: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityBowlingTeamSetupBinding.inflate(layoutInflater)
        setContentView(ui.root)
        currentPhotoPath = ""
        val batter1Name = intent.getStringExtra("batter1")
        val batter2Name = intent.getStringExtra("batter2")
        val batter3Name = intent.getStringExtra("batter3")
        val batter4Name = intent.getStringExtra("batter4")
        val batter5Name = intent.getStringExtra("batter5")
        val batter1Photo = intent.getStringExtra("batterPhoto1")
        val batter2Photo = intent.getStringExtra("batterPhoto2")
        val batter3Photo = intent.getStringExtra("batterPhoto3")
        val batter4Photo = intent.getStringExtra("batterPhoto4")
        val batter5Photo = intent.getStringExtra("batterPhoto5")
        Log.d("Bowling", "Batter1Photo: $batter1Photo")
        Log.d("Bowling", "Batter2Photo: $batter2Photo")
        Log.d("Bowling", "Batter3Photo: $batter3Photo")
        Log.d("Bowling", "Batter4Photo: $batter4Photo")
        Log.d("Bowling", "Batter5Photo: $batter5Photo")

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

        ui.bowlersInfoList.adapter = PlayerContainerAdapter(playerIndexes)
        ui.bowlersInfoList.layoutManager = LinearLayoutManager(this)

        ui.img6.setOnClickListener {
            targetImageView = ui.img6
            requestToTakePicture()
            photoPath6 = currentPhotoPath
        }
        ui.img7.setOnClickListener {
            targetImageView = ui.img7
            requestToTakePicture()
            photoPath7 = currentPhotoPath
        }
        ui.img8.setOnClickListener {
            targetImageView = ui.img8
            requestToTakePicture()
            photoPath8 = currentPhotoPath
        }
        ui.img9.setOnClickListener {
            targetImageView = ui.img9
            requestToTakePicture()
            photoPath9 = currentPhotoPath
        }
        ui.img10.setOnClickListener {
            targetImageView = ui.img10
            requestToTakePicture()
            photoPath10 = currentPhotoPath
        }

        ui.btnBack.setOnClickListener {
            finish()
        }
        ui.btnMatchStarts.setOnClickListener {
            if(teamSetupValidator.teamSetupValidation(ui)){
                teamSetupController.createValidationToast(
                    this,
                    "Please fill all the fields."
                )
            } else{
                val teamName = ui.txtBowlingTeamName.text.toString()
                val bowlers = teamSetupController.getBowlersFromView(ui)
                val team = Team(name = teamName, teamType = TeamType.BOWLING)

                teamSetupController.saveTeam(team)

                teamSetupController.savePlayers(bowlers, team)
                val bowlingTeamName = team.name
                val battingTeamName = intent.getStringExtra("battingTeamName")
                Log.d("BowlingTeamSetup", "battingTeamName: $battingTeamName")
                Log.d("BowlingTeamSetup", "bowlingTeamName: $bowlingTeamName")

                //get match from intent and save it to Firebase
                val matchId = "$battingTeamName vs $bowlingTeamName"
                val match = Match(
                    battingTeam = battingTeamName,
                    bowlingTeam = bowlingTeamName,
                    matchId = matchId,
                )
                teamSetupController.saveMatch(match)

                //pass intent to Scoring activity
                val i = Intent(this, Scoring::class.java)
                i.putExtra("matchId", matchId)
                i.putExtra("battingTeamName", battingTeamName)
                i.putExtra("bowlingTeamName", bowlingTeamName)
                i.putExtra("batter1", batter1Name)
                i.putExtra("batter2", batter2Name)
                i.putExtra("batter3", batter3Name)
                i.putExtra("batter4", batter4Name)
                i.putExtra("batter5", batter5Name)
                i.putExtra("batterPhoto1", batter1Photo)
                i.putExtra("batterPhoto2", batter2Photo)
                i.putExtra("batterPhoto3", batter3Photo)
                i.putExtra("batterPhoto4", batter4Photo)
                i.putExtra("batterPhoto5", batter5Photo)
                i.putExtra("bowlerPhoto6", photoPath6)
                i.putExtra("bowlerPhoto7", photoPath7)
                i.putExtra("bowlerPhoto8", photoPath8)
                i.putExtra("bowlerPhoto9", photoPath9)
                i.putExtra("bowlerPhoto10", photoPath10)
                for (index in 0 until bowlers.size){
                    i.putExtra("bowler${index + 1}", bowlers[index].name)
                }
                startActivity(i)
            }


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
                ui.img6 -> photoPath6 = currentPhotoPath
                ui.img7 -> photoPath7 = currentPhotoPath
                ui.img8 -> photoPath8 = currentPhotoPath
                ui.img9 -> photoPath9 = currentPhotoPath
                ui.img10 -> photoPath10 = currentPhotoPath
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