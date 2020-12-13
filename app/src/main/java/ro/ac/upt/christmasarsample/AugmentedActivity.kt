package ro.ac.upt.christmasarsample

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class AugmentedActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    private var renderable: Renderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augmented)

        initRenderableModel()


        arFragment = supportFragmentManager.findFragmentById(R.id.scf_central) as ArFragment

//        TODO("2. Invoke addRenderableToScene once a tap is executed over the AR plane")
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            addRenderableToScene(hitResult.createAnchor(), renderable!!)
        }



    }

    private fun initRenderableModel() {
        val modelUri = Uri.parse("model.sfb")

//        TODO("1. Init model renderable variable")
        ModelRenderable.builder() // To load as an asset from the 'assets' folder ('src/main/assets/andy.sfb'):
            .setSource(this, modelUri)
            .build()
            .thenAccept { loaded: ModelRenderable ->
                this.renderable = loaded
                Log.i(TAG, renderable.toString())
            }
            .exceptionally { throwable: Throwable? ->
                Log.e(TAG, "Unable to load Renderable.", throwable)
                null
            }


    }

    private fun addRenderableToScene(anchor: Anchor, renderable: Renderable) {
//        TODO("3. Build an anchor node and set the AR scene to be its parent")
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

//        TODO("4. Build an transformable node and set the previously anchor node to be its parent")
        val transformableNode = TransformableNode(arFragment.transformationSystem)
        transformableNode.setParent(anchorNode)


//        TODO("5. Assign node's renderable property to previously loaded renderable")
        transformableNode.renderable = renderable


    }

    companion object {
        private val TAG = AugmentedActivity::class.java.simpleName
    }

}
