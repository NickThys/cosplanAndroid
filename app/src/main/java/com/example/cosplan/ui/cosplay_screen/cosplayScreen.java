package com.example.cosplan.ui.cosplay_screen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosplan.R;

import com.example.cosplan.data.Coplay.CheckList.CheckListPartAdapter;
import com.example.cosplan.data.Coplay.CheckList.CheckListPartViewModel;
import com.example.cosplan.data.Coplay.CheckList.ChecklistPart;
import com.example.cosplan.data.Coplay.Cosplay;
import com.example.cosplan.data.Coplay.CosplayAdapter;
import com.example.cosplan.data.Coplay.CosplayViewModel;
import com.example.cosplan.data.Coplay.Part.Part;
import com.example.cosplan.data.Coplay.Part.PartAdapter;
import com.example.cosplan.data.Coplay.Part.PartViewModel;
import com.example.cosplan.data.Coplay.RefImg.RefenceImgAdapter;
import com.example.cosplan.data.Coplay.RefImg.ReferenceImg;
import com.example.cosplan.data.Coplay.RefImg.ReferenceImgViewModel;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPart;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPartAdapter;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPartViewModel;
import com.example.cosplan.data.Coplay.WIPImg.WIPImg;
import com.example.cosplan.data.Coplay.WIPImg.WIPImgAdapter;
import com.example.cosplan.data.Coplay.WIPImg.WIPImgViewModel;
import com.example.cosplan.data.Coplay.Webshop.Webshop;
import com.example.cosplan.data.Coplay.Webshop.WebshopAdapter;
import com.example.cosplan.data.Coplay.Webshop.WebshopViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class cosplayScreen extends Fragment implements AdapterView.OnItemSelectedListener {

    private CosplayViewModel cosplayViewModel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CosplayAdapter cosplayAdapter;
    private PartViewModel partViewModel;
    private ReferenceImgViewModel referenceImgViewModel;
    private WebshopAdapter mWebshopAdapter;
    private WebshopViewModel mWebshopViewModel;
    private CheckListPartViewModel mCheckListPartViewModel;
    private CheckListPartAdapter mCheckListPartAdapter;
    private ShoppingListPartAdapter mShoppingListPartAdapter;
    private ShoppingListPartViewModel mShoppingListViewModel;
    private WIPImgViewModel wipImgViewModel;

    private TextView mName, mStartDate, mEndDate, mPercentage, mBudget;
    private ImageView mImage;
    private ImageButton mUpdateCosplay;
    private EditText mCosplayName, mCosplayStartDate, mCosplayEndDate, mCosplayBudget;
    private EditText mPartName, mPartLink, mPartCost, mPartEndDate, mCosplayNote;
    private Spinner mPartmakeBuy;
    private ImageView mPartImage;
    private Button mPartChooseImage, mPartCancel, mPartAddPart, mCosplayNotesSave, mRefImgAdd, mCheckListPartClear,mWIPImgAddPicture,mWIPImgTakePicture;
    private FloatingActionButton mfabAddPart, mCheckListPartAdd;
    private RecyclerView mRVRefImg,mRVWIPImg;
    private ImageView mCosplayImage;
    private Button mChoosePicture, mCancel, mUpdateCosplays, mCosplayParts, mCosplayNotes, mCosplayRefPic, mCosplayWIPPic, mCosplayChecklist, mCosplayShoppinglist, mCosplayWebshop, mCosplayEvents, mShoppingListAdd,mShoppingListCancel,mShoppingListClear;
    private RecyclerView mRecViewCosplayWebshop, mRVCheckListPart,mRVShoppingList;
    private FloatingActionButton mFabAddCosplayWebshop,mFabShoppingListAdd;
    private Cosplay tempCosplay=null;
    private RefenceImgAdapter refenceImgAdapter=null;
    private WIPImgAdapter wipImgAdapter=null;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE_PART = 2;
    private static final int GALLERY_REQUEST_CODE_REF_IMG = 3;
    private static final int GALLERY_REQUEST_CODE_WIP_IMG = 4;
    private static final int CAMERA_REQUEST_CODE_WIP_IMG = 5;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cosplay_screen, container, false);
        //Views of all the fragments
        final View PartsView = inflater.inflate(R.layout.cosplay_screen_parts, container, false);
        final View RefImgView = inflater.inflate(R.layout.cosplay_screen_ref_img, container, false);
        final View ShoppingListView = inflater.inflate(R.layout.cosplay_screen_shopping_list, container, false);
        final View NotesView = inflater.inflate(R.layout.cosplay_screen_notes, container, false);
        final View CheckListView = inflater.inflate(R.layout.cosplay_screen_checklist, container, false);
        final View WipImgView = inflater.inflate(R.layout.cosplay_screen_wip_img, container, false);
        final View EventsView = inflater.inflate(R.layout.cosplay_screen_events, container, false);
        final View WebshopsView = inflater.inflate(R.layout.cosplay_screen_webshops, container, false);

        refenceImgAdapter = new RefenceImgAdapter(null, requireContext());
        wipImgAdapter=new WIPImgAdapter(null,requireContext());
        final PartAdapter partAdapterMake = new PartAdapter(requireContext());
        final PartAdapter partAdapterBuy = new PartAdapter(requireContext());
        final ShoppingListPartAdapter shoppingListPartAdapter=new ShoppingListPartAdapter(requireContext(),getActivity().getApplication());

        cosplayViewModel = new ViewModelProvider(this).get(CosplayViewModel.class);
        tempCosplay = cosplayScreenArgs.fromBundle(getArguments()).getCurrentCosplay();
        final ViewGroup fl = v.findViewById(R.id.FrameLayout_Content);
        //Initial view for the framelayout
        fl.addView(PartsView);
        cosplayAdapter = new CosplayAdapter(requireContext());

        //Items from the header
        mName = v.findViewById(R.id.TextView_CosplayHeaderName);
        mEndDate = v.findViewById(R.id.TextView_CosplayHeaderEndDate);
        mPercentage = v.findViewById(R.id.TextView_CosplayHeaderPercentage);
        mBudget = v.findViewById(R.id.TextView_CosplayHeaderBudget);
        mImage = v.findViewById(R.id.ImageView_CosplayHeaderImage);
        mUpdateCosplay = v.findViewById(R.id.ImageButton_CosplayHeaderUpdate);
        //Items from the button bar
        mCosplayParts = v.findViewById(R.id.Btn_BtnBar_CosplayParts);
        mCosplayNotes = v.findViewById(R.id.Btn_BtnBar_CosplayNotes);
        mCosplayRefPic = v.findViewById(R.id.Btn_BtnBar_CosplayRefImage);
        mCosplayWIPPic = v.findViewById(R.id.Btn_BtnBar_CosplayWIPImage);
        mCosplayChecklist = v.findViewById(R.id.Btn_BtnBar_CosplayChecklist);
        mCosplayShoppinglist = v.findViewById(R.id.Btn_BtnBar_CosplayShoppinglist);
        mCosplayWebshop = v.findViewById(R.id.Btn_BtnBar_CosplayWebshops);
        mCosplayEvents = v.findViewById(R.id.Btn_BtnBar_CosplayEvents);
        //Items from the Notes
        mCosplayNote = NotesView.findViewById(R.id.EditText_CosplayNoteText);
        mCosplayNotesSave = NotesView.findViewById(R.id.Btn_CosplayNoteSave);
        //Items from the Ref Img
        mRVRefImg = RefImgView.findViewById(R.id.RecView_RefImg);
        mRefImgAdd = RefImgView.findViewById(R.id.Btn_RefImgAdd);
        //items from the webshops
        mRecViewCosplayWebshop = WebshopsView.findViewById(R.id.RecView_CosplayWebshop);
        mFabAddCosplayWebshop = WebshopsView.findViewById(R.id.Fab_CosplayWebshopAdd);
        //items from the Checklist,
        mRVCheckListPart = CheckListView.findViewById(R.id.RecView_CheckList);
        mCheckListPartAdd = CheckListView.findViewById(R.id.FAB_CheckListAdd);
        mCheckListPartClear = CheckListView.findViewById(R.id.Btn_CheckListClearCheckBox);
        //items from the ShoppingList;
        mRVShoppingList=ShoppingListView.findViewById(R.id.RecView_Shoppinglist);
        mFabShoppingListAdd =ShoppingListView.findViewById(R.id.Fab_ShoppinglistAdd);
        mShoppingListClear=ShoppingListView.findViewById(R.id.Btn_ShoppinglistClear);
        //items from the WIP img
        mRVWIPImg=WipImgView.findViewById(R.id.RecView_WipImages);
        mWIPImgAddPicture=WipImgView.findViewById(R.id.Btn_WipImagesGetImage);
        mWIPImgTakePicture=WipImgView.findViewById(R.id.Btn_WipImagesTakePicture);

        //Header
        //Adding text to the items from the header
        mName.setText(tempCosplay.mCosplayName);
        mEndDate.setText(tempCosplay.mCosplayEndDate);
        mBudget.setText(Double.toString(tempCosplay.mCosplayBudget));
        mImage.setImageBitmap(tempCosplay.mCosplayIMG);
        mPercentage.setText("% complete");
        mfabAddPart = v.findViewById(R.id.Fab_PartsAdd);

        //onclick listener from the header
        mUpdateCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCosplayDialog(tempCosplay);
            }
        });

        //Button Bar
        //onclick listener from the buttons
        mCosplayParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(PartsView);
            }
        });
        mCosplayRefPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(RefImgView);
            }
        });
        mCosplayShoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(ShoppingListView);
            }
        });
        mCosplayNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(NotesView);

            }
        });
        mCosplayChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(CheckListView);
            }
        });
        mCosplayWIPPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(WipImgView);
            }
        });
        mCosplayEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(EventsView);
            }
        });
        mCosplayWebshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                fl.addView(WebshopsView);
            }
        });

        //Part View
        //recyclerview Make
        RecyclerView recyclerViewMake = v.findViewById(R.id.RecView_PartsToMake);
        recyclerViewMake.setAdapter(partAdapterMake);
        recyclerViewMake.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewMake.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helperMake = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // TODO: 11/11/2020 Add delete on swipe
            }
        });
        helperMake.attachToRecyclerView(recyclerViewMake);
        partViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        partViewModel.getAllPartsToMake(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                partAdapterMake.setParts(parts);
            }
        });
        //recyclerview buy
        final RecyclerView recyclerViewBuy = v.findViewById(R.id.RecView_PartsToBuy);
        recyclerViewBuy.setAdapter(partAdapterBuy);
        recyclerViewBuy.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewBuy.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper helperBuy = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // TODO: 11/11/2020 Add delete on swipe
            }
        });
        helperBuy.attachToRecyclerView(recyclerViewBuy);
        partViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        partViewModel.getAllPartsToBuy(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                partAdapterBuy.setParts(parts);
            }
        });
        //fab to add a new cosplay part
        mfabAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewPartDialog(tempCosplay);
            }
        });


        //Notes
        mCosplayNote.setText(tempCosplay.mCosplayNote);
        mCosplayNotesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cosplay CosUP = new Cosplay(tempCosplay.mCosplayId, tempCosplay.mCosplayName, tempCosplay.mCosplayStartDate, tempCosplay.mCosplayEndDate, tempCosplay.mCosplayBudget, tempCosplay.mCosplayIMG, mCosplayNote.getText().toString());

                cosplayViewModel.update(CosUP);
                closeKeyboard(v);
                Toast.makeText(requireContext(), "The note is saved", Toast.LENGTH_SHORT).show();
            }
        });
        //RefImg
        setRefImageInGrid(tempCosplay, refenceImgAdapter);
        mRefImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addcospimg to db
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE_REF_IMG);
            }
        });

        //webshops
        mWebshopAdapter = new WebshopAdapter(requireContext(), getActivity().getApplication());
        mRecViewCosplayWebshop.setAdapter(mWebshopAdapter);
        mRecViewCosplayWebshop.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecViewCosplayWebshop.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mHelperWebshop = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Webshop myWebshop = mWebshopAdapter.getWebshopAtPosition(position);
                Toast.makeText(getContext(), "Deleting " + myWebshop.getCosplayWebshopName(), Toast.LENGTH_SHORT).show();
                mWebshopViewModel.delete(myWebshop);
            }
        });
        mHelperWebshop.attachToRecyclerView(mRecViewCosplayWebshop);
        mWebshopViewModel = new ViewModelProvider(this).get(WebshopViewModel.class);
        mWebshopViewModel.getAllWebshops(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<Webshop>>() {
            @Override
            public void onChanged(List<Webshop> webshops) {
                mWebshopAdapter.setWebshops(webshops);
            }
        });
        mFabAddCosplayWebshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayWebshopDialog(tempCosplay);
            }
        });

        //CheckList
        mCheckListPartAdapter = new CheckListPartAdapter(requireContext(), getActivity().getApplication());
        mRVCheckListPart.setAdapter(mCheckListPartAdapter);
        mRVCheckListPart.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mHelperCheckListPart = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ChecklistPart myCheckListPart = mCheckListPartAdapter.getChecklistPartAtPosition(position);
                deleteCheckListPartDialog(myCheckListPart);
            }
        });
        mHelperCheckListPart.attachToRecyclerView(mRVCheckListPart);
        mCheckListPartViewModel = new ViewModelProvider(this).get(CheckListPartViewModel.class);
        final List<ChecklistPart> mAllCheckListParts = new LinkedList<ChecklistPart>();
        mCheckListPartViewModel.getAllCheckListParts(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<ChecklistPart>>() {
            @Override
            public void onChanged(List<ChecklistPart> checklistParts) {
                mCheckListPartAdapter.setCheckListParts(checklistParts);
                mAllCheckListParts.addAll(checklistParts);
            }
        });

        mCheckListPartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayChecklistPartDialog(tempCosplay);
            }
        });
        mCheckListPartClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListClearCheckBoxes(mAllCheckListParts);
            }
        });

        //Shoppinglist
        mShoppingListPartAdapter=new ShoppingListPartAdapter(requireContext(),getActivity().getApplication());
        mRVShoppingList.setAdapter(mShoppingListPartAdapter);
        mRVShoppingList.setLayoutManager(new LinearLayoutManager(requireContext()));

        ItemTouchHelper mHelperShoppingList=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                ShoppingListPart myShoppingList=mShoppingListPartAdapter.getShoppingListPartAtPosition(position);
                deleteShoppingListPartDialog(myShoppingList);
            }
        });
        mHelperShoppingList.attachToRecyclerView(mRVShoppingList);
        mShoppingListViewModel=new ViewModelProvider(this).get(ShoppingListPartViewModel.class);
        mShoppingListViewModel.getAllShoppingListParts(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<ShoppingListPart>>() {
            @Override
            public void onChanged(List<ShoppingListPart> shoppingListParts) {
                mShoppingListPartAdapter.setShoppingListParts(shoppingListParts);
            }
        });

        mFabShoppingListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayShoppingListPartDialog(tempCosplay);
            }
        });
        mShoppingListClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWholeShoppingListDialog(mShoppingListPartAdapter.getShoppingListPartAtPosition(0));
            }
        });

        //WIP Images
        setWipImagesInGrid(tempCosplay,wipImgAdapter);
        mWIPImgAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE_WIP_IMG);
            }
        });
        mWIPImgTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE_WIP_IMG);
            }
        });

        return v;
    }

    public void checkListClearCheckBoxes(List<ChecklistPart> allParts) {
        for (ChecklistPart part : allParts) {
            ChecklistPart temp = new ChecklistPart(part.mCosplayId, part.mCosplayCheckListPartId, part.mCosplayCheckListPartName, false);
            mCheckListPartViewModel.update(temp);
        }
    }

    public void setRefImageInGrid(Cosplay tempCosplay, final RefenceImgAdapter refenceImgAdapter) {
        referenceImgViewModel = new ViewModelProvider(this).get(ReferenceImgViewModel.class);
        referenceImgViewModel.GetAllRefImg(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<ReferenceImg>>() {
            @Override
            public void onChanged(List<ReferenceImg> referenceImgs) {
                refenceImgAdapter.setRefImg(referenceImgs);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false);
        mRVRefImg.setLayoutManager(gridLayoutManager);
        mRVRefImg.setAdapter(refenceImgAdapter);
    }
   public void setWipImagesInGrid(Cosplay tempCosplay, final WIPImgAdapter wipImgAdapter){
        wipImgViewModel=new ViewModelProvider(this).get(WIPImgViewModel.class);
        wipImgViewModel.getAllWIPImgs(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<WIPImg>>() {
            @Override
            public void onChanged(List<WIPImg> wipImgs) {
                wipImgAdapter.setWIPImgs(wipImgs);
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false);
        mRVWIPImg.setLayoutManager(gridLayoutManager);
        mRVWIPImg.setAdapter(wipImgAdapter);
   }
   //All dialogs
    public void deleteShoppingListPartDialog(final ShoppingListPart mShoppingListPart) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View deleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart) + mShoppingListPart.mCosplayShoppingListPartName);
        Button yes, no;
        no = deleteCosplayView.findViewById(R.id.Btn_DeleteNo);
        yes = deleteCosplayView.findViewById(R.id.Btn_DeleteYes);
        dialogBuilder.setView(deleteCosplayView);
        dialog = dialogBuilder.create();
        dialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShoppingListViewModel.delete(mShoppingListPart);
                Toast.makeText(requireContext(), mShoppingListPart.mCosplayShoppingListPartName + " deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().recreate();
            }
        });
    }
    public void deleteCheckListPartDialog(final ChecklistPart mCheckListPart) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View deleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart) + mCheckListPart.mCosplayCheckListPartName);
        Button yes, no;
        no = deleteCosplayView.findViewById(R.id.Btn_DeleteNo);
        yes = deleteCosplayView.findViewById(R.id.Btn_DeleteYes);
        dialogBuilder.setView(deleteCosplayView);
        dialog = dialogBuilder.create();
        dialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckListPartViewModel.delete(mCheckListPart);
                Toast.makeText(requireContext(), mCheckListPart.mCosplayCheckListPartName + " deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().recreate();
            }
        });
    }
    public void UpdateCosplayDialog(final Cosplay cosplay) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View cosplayPopUpView = getLayoutInflater().inflate(R.layout.add_cosplay, null);
        mCosplayName = cosplayPopUpView.findViewById(R.id.EditText_NewCosplayName);
        mCosplayStartDate = cosplayPopUpView.findViewById(R.id.EditText_NewCosplayBeginDate);
        mCosplayEndDate = cosplayPopUpView.findViewById(R.id.EditText_NewCosplayEndDate);
        mCosplayBudget = cosplayPopUpView.findViewById(R.id.EditText_NewCosplayBudget);
        mCosplayImage = cosplayPopUpView.findViewById(R.id.ImageView_NewCosplayImgPreview);
        mChoosePicture = cosplayPopUpView.findViewById(R.id.Btn_NewCosplayChooseImg);
        mCancel = cosplayPopUpView.findViewById(R.id.Btn_NewCosplayCancel);
        mUpdateCosplays = cosplayPopUpView.findViewById(R.id.Btn_NewCosplayAdd);
        dialogBuilder.setView(cosplayPopUpView);

        mUpdateCosplays.setText("Update Cosplay");
        mCosplayName.setText(cosplay.mCosplayName);
        mCosplayStartDate.setText(cosplay.mCosplayStartDate);
        mCosplayEndDate.setText(cosplay.mCosplayEndDate);
        mCosplayBudget.setText(Double.toString(cosplay.mCosplayBudget));
        mCosplayImage.setImageBitmap(cosplay.mCosplayIMG);

        dialog = dialogBuilder.create();
        dialog.show();

        //create dateSelector and add the selected date to the Edit text
        mCosplayStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    int year;
                    int month;
                    int day;
                    String mtemp = mCosplayStartDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mCosplayStartDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mStartDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayStartDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        //create dateSelector and add the selected date to the Edit text
        mCosplayEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mCosplayEndDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mCosplayEndDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayEndDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        //Cancel. dismiss the popup
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Choose the picture from the gallery

        mChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE);

            }
        });
        //Add Copslay to the database
        mUpdateCosplays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cosplay CosUP = new Cosplay(cosplay.mCosplayId, mCosplayName.getText().toString(), mCosplayStartDate.getText().toString(), mCosplayEndDate.getText().toString(), Double.parseDouble(mCosplayBudget.getText().toString()), ((BitmapDrawable) mCosplayImage.getDrawable()).getBitmap(), mCosplayNote.getText().toString());

                cosplayViewModel.update(CosUP);
                dialog.dismiss();
                mName.setText(CosUP.mCosplayName);
                mEndDate.setText(CosUP.mCosplayEndDate);
                mBudget.setText(Double.toString(CosUP.mCosplayBudget));
                mImage.setImageBitmap(CosUP.mCosplayIMG);
                mPercentage.setText("% complete");
                mCosplayNote.setText(CosUP.mCosplayNote);
            }
        });

    }
    public void addNewCosplayWebshopDialog(final Cosplay cosplay) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View WebshopPopUpView = getLayoutInflater().inflate(R.layout.cosplay_webshop, null);
        final EditText mSiteName, mSiteLink;
        Button mCancel, mAdd;
        mSiteLink = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
        mSiteName = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
        mAdd = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
        mCancel = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);
        dialogBuilder.setView(WebshopPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Webshop temp = new Webshop(cosplay.mCosplayId, 0, mSiteName.getText().toString(), mSiteLink.getText().toString());
                mWebshopViewModel.insert(temp);
                dialog.dismiss();
            }
        });
    }
    public void addNewCosplayChecklistPartDialog(final Cosplay cosplay) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View checkListPopUpView = getLayoutInflater().inflate(R.layout.cosplay_checklist_addpart, null);
        final EditText mCheckListPartName;
        final Button mCheckListCancel, mChecklistAdd;
        mCheckListPartName = checkListPopUpView.findViewById(R.id.EditText_NewChecklistPartName);
        mCheckListCancel = checkListPopUpView.findViewById(R.id.Btn_NewChecklistPartCancel);
        mChecklistAdd = checkListPopUpView.findViewById(R.id.Btn_NewChecklistPartAdd);

        dialogBuilder.setView(checkListPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        mCheckListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mChecklistAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChecklistPart temp = new ChecklistPart(cosplay.mCosplayId, 0, mCheckListPartName.getText().toString(), false);
                mCheckListPartViewModel.insert(temp);

                dialog.dismiss();
            }
        });
    }
    public void addNewCosplayShoppingListPartDialog(final Cosplay cosplay) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View shoppingListPopUpView = getLayoutInflater().inflate(R.layout.cosplay_shoppinglist_add, null);

        final EditText mShoppingListPartName,mShoppingListShop;
        final Button mShoppingListCancel, mShoppinglistAdd;
        mShoppingListPartName = shoppingListPopUpView.findViewById(R.id.EditText_NewShoppingListName);
        mShoppingListCancel = shoppingListPopUpView.findViewById(R.id.Btn_NewShoppingListCancel);
        mShoppinglistAdd = shoppingListPopUpView.findViewById(R.id.Btn_NewShoppingListAdd);
        mShoppingListShop=shoppingListPopUpView.findViewById(R.id.EditText_NewShoppingListShop);

        dialogBuilder.setView(shoppingListPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        mShoppingListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mShoppinglistAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListPart temp = new ShoppingListPart(cosplay.mCosplayId, 0, mShoppingListPartName.getText().toString(), mShoppingListShop.getText().toString(),false);
                mShoppingListViewModel.insert(temp);

                dialog.dismiss();
            }
        });
    }
    public void createNewPartDialog(final Cosplay cosplay) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View PartPopUpView = getLayoutInflater().inflate(R.layout.add_cosplay_part, null);

        mPartName = PartPopUpView.findViewById(R.id.EditText_NewPartName);

        mPartmakeBuy = PartPopUpView.findViewById(R.id.Spinner_NewPartBuyMake);
        if (mPartmakeBuy != null) {
            mPartmakeBuy.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.BuyMake, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartmakeBuy.setAdapter(adapter);
        mPartLink = PartPopUpView.findViewById(R.id.EditText_NewPartLink);
        mPartEndDate = PartPopUpView.findViewById(R.id.EditText_NewPartEndDate);
        mPartCost = PartPopUpView.findViewById(R.id.EditText_NewPartCost);
        mPartImage = PartPopUpView.findViewById(R.id.ImageView_NewPartImgPreview);
        mPartChooseImage = PartPopUpView.findViewById(R.id.Btn_NewPartChoosePartImg);
        mPartCancel = PartPopUpView.findViewById(R.id.Btn_NewPartCancel);
        mPartAddPart = PartPopUpView.findViewById(R.id.Btn_NewPartAdd);
        //mPartChooseImage.setEnabled(false);
        dialogBuilder.setView(PartPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();


        //create dateSelector and add the selected date to the Edit text
        mPartEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mPartEndDate.getText().toString().trim();
                    if (mtemp.matches("")) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mPartEndDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mPartEndDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        //Cancel. dismiss the popup
        mPartCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Choose the picture from the gallery
        mPartChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), GALLERY_REQUEST_CODE_PART);

            }
        });
        mPartAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Part temp = new Part();
                temp.mCosplayId = cosplay.mCosplayId;
                temp.mCosplayPartId = 0;
                temp.mCosplayPartName = mPartName.getText().toString();
                temp.mCosplayPartBuyMake = mPartmakeBuy.getSelectedItem().toString();
                temp.mCosplayPartLink = mPartLink.getText().toString();
                temp.mCosplayPartCost = Double.parseDouble(mPartCost.getText().toString());
                temp.mCosplayPartEndDate = mPartEndDate.getText().toString();
                temp.mCosplayPartImg =((BitmapDrawable) mPartImage.getDrawable()).getBitmap()  ;
                temp.mCosplayPartStatus = "Planned";
                partViewModel.insert(temp);
                dialog.dismiss();
            }
        });
    }
    public void deleteWholeShoppingListDialog(final ShoppingListPart part){
        dialogBuilder = new AlertDialog.Builder(requireContext());
        final View deleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart) +" the whole list?");
        final Button yes, no;
        no = deleteCosplayView.findViewById(R.id.Btn_DeleteNo);
        yes = deleteCosplayView.findViewById(R.id.Btn_DeleteYes);
        dialogBuilder.setView(deleteCosplayView);
        dialog = dialogBuilder.create();
        dialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShoppingListViewModel.deleteAll(part);
                Toast.makeText(requireContext(), "Shopping list  deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getActivity().recreate();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri imageData;

        if (requestCode == GALLERY_REQUEST_CODE&&data!=null) {

                imageData = data.getData();

                mCosplayImage.setImageURI(imageData);

        }
        if (requestCode==GALLERY_REQUEST_CODE_PART&&data!=null){
            imageData=data.getData();
            mPartImage.setImageURI(imageData);
        }
        if (requestCode==GALLERY_REQUEST_CODE_REF_IMG&&data!=null){
            imageData=data.getData();
            InputStream imagestream=null;
            try {
               imagestream =getContext().getContentResolver().openInputStream(imageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ReferenceImg temp = new ReferenceImg(tempCosplay.mCosplayId, 0,BitmapFactory.decodeStream(imagestream) );
            referenceImgViewModel.insert(temp);
            setRefImageInGrid(tempCosplay, refenceImgAdapter);
        }
        if (requestCode==GALLERY_REQUEST_CODE_WIP_IMG&&data!=null){
            imageData=data.getData();
            InputStream imageStream=null;
            try {
                imageStream=getContext().getContentResolver().openInputStream(imageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            WIPImg temp=new WIPImg(tempCosplay.mCosplayId,0,BitmapFactory.decodeStream(imageStream));
            wipImgViewModel.insert(temp);
            setWipImagesInGrid(tempCosplay,wipImgAdapter);
        }
        if (requestCode==CAMERA_REQUEST_CODE_WIP_IMG&&data!=null){
            Bitmap img=(Bitmap)data.getExtras().get("data");

            WIPImg temp=new WIPImg(tempCosplay.mCosplayId,0,img);
            wipImgViewModel.insert(temp);
            setWipImagesInGrid(tempCosplay,wipImgAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}