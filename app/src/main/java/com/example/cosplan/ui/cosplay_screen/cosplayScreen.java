package com.example.cosplan.ui.cosplay_screen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
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

import com.example.cosplan.data.cosplay.checkList.CheckListPartAdapter;
import com.example.cosplan.data.cosplay.checkList.CheckListPartViewModel;
import com.example.cosplan.data.cosplay.checkList.ChecklistPart;
import com.example.cosplan.data.cosplay.Cosplay;
import com.example.cosplan.data.cosplay.CosplayViewModel;
import com.example.cosplan.data.cosplay.events.Event;
import com.example.cosplan.data.cosplay.events.EventAdapter;
import com.example.cosplan.data.cosplay.events.EventViewModel;
import com.example.cosplan.data.cosplay.part.Part;
import com.example.cosplan.data.cosplay.part.PartAdapter;
import com.example.cosplan.data.cosplay.part.PartViewModel;
import com.example.cosplan.data.cosplay.refImg.ReferenceImgAdapter;
import com.example.cosplan.data.cosplay.refImg.ReferenceImg;
import com.example.cosplan.data.cosplay.refImg.ReferenceImgViewModel;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPart;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPartAdapter;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPartViewModel;
import com.example.cosplan.data.cosplay.wIPImg.WIPImg;
import com.example.cosplan.data.cosplay.wIPImg.WIPImgAdapter;
import com.example.cosplan.data.cosplay.wIPImg.WIPImgViewModel;
import com.example.cosplan.data.cosplay.webshop.Webshop;
import com.example.cosplan.data.cosplay.webshop.WebshopAdapter;
import com.example.cosplan.data.cosplay.webshop.WebshopViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class cosplayScreen extends Fragment implements AdapterView.OnItemSelectedListener {

    private CosplayViewModel mCosplayViewModel;
    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mDialog;
    private PartViewModel mPartViewModel;
    private PartAdapter mPartAdapterBuy, mPartAdapterMake;
    private ReferenceImgViewModel mReferenceImgViewModel;
    private ReferenceImgAdapter mReferenceImgAdapter = null;
    private WebshopAdapter mWebshopAdapter;
    private WebshopViewModel mWebshopViewModel;
    private CheckListPartViewModel mCheckListPartViewModel;
    private CheckListPartAdapter mCheckListPartAdapter;
    private ShoppingListPartAdapter mShoppingListPartAdapter;
    private ShoppingListPartViewModel mShoppingListViewModel;
    private WIPImgViewModel mWipImgViewModel;
    private WIPImgAdapter mWipImgAdapter = null;
    private EventViewModel mEventViewModel;
    private EventAdapter mEventConventionAdapter, mEventShootAdapter, mEventCharityAdapter;
    private TextView mName, mEndDate, mPercentage, mBudget;
    private ImageView mImage, mPartImage, mCosplayImage;
    private EditText mCosplayName, mCosplayStartDate, mCosplayEndDate, mCosplayBudget, mPartName, mPartLink, mPartCost, mPartEndDate, mCosplayNote;
    private Spinner mSpinnerPartMakeBuy;
    private RecyclerView mRVRefImg, mRecViewWIPImg;
    private String mPartUri,mCosplayUri;

    private Cosplay mTempCosplay = null;

    private DatePickerDialog.OnDateSetListener mStartDateSetListener, mEndDateSetListener;
    private static final int GALLERY_REQUEST_CODE = 1,
            GALLERY_REQUEST_CODE_PART = 2,
            GALLERY_REQUEST_CODE_REF_IMG = 3,
            GALLERY_REQUEST_CODE_WIP_IMG = 4,
            CAMERA_REQUEST_CODE_WIP_IMG = 5;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private List<Part> mListMake;
    private List<Part> mListBuy;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListMake = new ArrayList<>();
        mListBuy = new ArrayList<>();
        // Inflate the layout for this fragment
        View mRoot = inflater.inflate(R.layout.fragment_cosplay_screen, container, false);
        //region Views of all the fragments
        final View PartsView = inflater.inflate(R.layout.cosplay_screen_parts, container, false);
        final View RefImgView = inflater.inflate(R.layout.cosplay_screen_ref_img, container, false);
        final View ShoppingListView = inflater.inflate(R.layout.cosplay_screen_shopping_list, container, false);
        final View NotesView = inflater.inflate(R.layout.cosplay_screen_notes, container, false);
        final View CheckListView = inflater.inflate(R.layout.cosplay_screen_checklist, container, false);
        final View WipImgView = inflater.inflate(R.layout.cosplay_screen_wip_img, container, false);
        final View EventsView = inflater.inflate(R.layout.cosplay_screen_events, container, false);
        final View WebshopsView = inflater.inflate(R.layout.cosplay_screen_webshops, container, false);
        //endregion


        mReferenceImgAdapter = new ReferenceImgAdapter(null, requireContext(), getActivity().getApplication());
        mWipImgAdapter = new WIPImgAdapter(null, requireContext(), getActivity().getApplication());
        mPartAdapterMake = new PartAdapter(requireContext(), getActivity().getApplication());
        mPartAdapterBuy = new PartAdapter(requireContext(), getActivity().getApplication());
        mEventConventionAdapter = new EventAdapter(requireContext(), getActivity().getApplication());
        mEventShootAdapter = new EventAdapter(requireContext(), getActivity().getApplication());
        mEventCharityAdapter = new EventAdapter(requireContext(), getActivity().getApplication());

        mCosplayViewModel = new ViewModelProvider(this).get(CosplayViewModel.class);
        if (getArguments() != null) {
            mTempCosplay = cosplayScreenArgs.fromBundle(getArguments()).getCurrentCosplay();
        }
        final ViewGroup mFrameLayoutContent = mRoot.findViewById(R.id.FrameLayout_Content);
        //Initial view for the framelayout
        mFrameLayoutContent.addView(PartsView);
        mPartAdapterBuy.setCosplay(mTempCosplay, mCosplayViewModel, mRoot);
        mPartAdapterMake.setCosplay(mTempCosplay, mCosplayViewModel, mRoot);

        //region initiate parts
        //Items from the header
        mName = mRoot.findViewById(R.id.TextView_CosplayHeaderName);
        mEndDate = mRoot.findViewById(R.id.TextView_CosplayHeaderEndDate);
        mPercentage = mRoot.findViewById(R.id.TextView_CosplayHeaderPercentage);
        mBudget = mRoot.findViewById(R.id.TextView_CosplayHeaderBudget);
        mImage = mRoot.findViewById(R.id.ImageView_CosplayHeaderImage);
        ImageButton mUpdateCosplay = mRoot.findViewById(R.id.ImageButton_CosplayHeaderUpdate);
        //Items from the button bar
        Button mCosplayParts = mRoot.findViewById(R.id.Btn_BtnBar_CosplayParts);
        Button mCosplayNotes = mRoot.findViewById(R.id.Btn_BtnBar_CosplayNotes);
        Button mCosplayRefPic = mRoot.findViewById(R.id.Btn_BtnBar_CosplayRefImage);
        Button mCosplayWIPPic = mRoot.findViewById(R.id.Btn_BtnBar_CosplayWIPImage);
        Button mCosplayChecklist = mRoot.findViewById(R.id.Btn_BtnBar_CosplayChecklist);
        Button mCosplayShoppinglist = mRoot.findViewById(R.id.Btn_BtnBar_CosplayShoppinglist);
        Button mCosplayWebshop = mRoot.findViewById(R.id.Btn_BtnBar_CosplayWebshops);
        Button mCosplayEvents = mRoot.findViewById(R.id.Btn_BtnBar_CosplayEvents);
        //Items from the Notes
        mCosplayNote = NotesView.findViewById(R.id.EditText_CosplayNoteText);
        Button mCosplayNotesSave = NotesView.findViewById(R.id.Btn_CosplayNoteSave);
        //Items from the Ref Img
        mRVRefImg = RefImgView.findViewById(R.id.RecView_RefImg);
        Button mRefImgAdd = RefImgView.findViewById(R.id.Btn_RefImgAdd);
        //items from the webshops
        RecyclerView mRecViewCosplayWebshop = WebshopsView.findViewById(R.id.RecView_CosplayWebshop);
        FloatingActionButton mFabAddCosplayWebshop = WebshopsView.findViewById(R.id.Fab_CosplayWebshopAdd);
        //items from the Checklist,
        RecyclerView mRecViewCheckListPart = CheckListView.findViewById(R.id.RecView_CheckList);
        FloatingActionButton mCheckListPartAdd = CheckListView.findViewById(R.id.FAB_CheckListAdd);
        Button mCheckListPartClear = CheckListView.findViewById(R.id.Btn_CheckListClearCheckBox);
        Button mCheckListExportToPDF = CheckListView.findViewById(R.id.Btn_CheckListExportToPDF);
        //items from the ShoppingList;
        RecyclerView mRecViewShoppingList = ShoppingListView.findViewById(R.id.RecView_Shoppinglist);
        FloatingActionButton mFabShoppingListAdd = ShoppingListView.findViewById(R.id.Fab_ShoppinglistAdd);
        Button mShoppingListClear = ShoppingListView.findViewById(R.id.Btn_ShoppinglistClear);
        //items from the WIP img
        mRecViewWIPImg = WipImgView.findViewById(R.id.RecView_WipImages);
        Button mWIPImgAddPicture = WipImgView.findViewById(R.id.Btn_WipImagesGetImage);
        Button mWIPImgTakePicture = WipImgView.findViewById(R.id.Btn_WipImagesTakePicture);
        //items from the events
        RecyclerView mRecViewEventsConvention = EventsView.findViewById(R.id.RecView_EventConvention);
        RecyclerView mRecViewEventsShoots = EventsView.findViewById(R.id.RecView_EventShoot);
        RecyclerView mRecViewEventsCharity = EventsView.findViewById(R.id.RecView_EventCharity);
        FloatingActionButton mFabEventsAdd = EventsView.findViewById(R.id.Fab_EventAdd);
        //endregion

        //region Header
        //Adding text to the items from the header
        mName.setText(mTempCosplay.mCosplayName);
        mEndDate.setText(mTempCosplay.mCosplayEndDate);
        updateCosplayHeaderBudget();
        SetImageFromUri(mImage,mTempCosplay.mCosplayIMG);

        updateCosplayPercentage();
        mPercentage.setText(String.format("%s%%", mTempCosplay.mCosplayPercentage));
        FloatingActionButton mFabAddPart = mRoot.findViewById(R.id.Fab_PartsAdd);

        //onclick listener from the header
        mUpdateCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCosplayDialog(mTempCosplay);
            }
        });
        //endregion

        //region Button Bar
        //onclick listener from the buttons
        mCosplayParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(PartsView);
            }
        });
        mCosplayRefPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(RefImgView);
            }
        });
        mCosplayShoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(ShoppingListView);
            }
        });
        mCosplayNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(NotesView);
            }
        });
        mCosplayChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(CheckListView);
            }
        });
        mCosplayWIPPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(WipImgView);
            }
        });
        mCosplayEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(EventsView);
            }
        });
        mCosplayWebshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayoutContent.removeAllViews();
                mFrameLayoutContent.addView(WebshopsView);
            }
        });
        //endregion
        List<Part> mPartsList = new ArrayList<>();
        mPartsList.clear();
        //region Part View
        //recyclerview Make
        RecyclerView mRecyclerViewMake = mRoot.findViewById(R.id.RecView_PartsToMake);
        mRecyclerViewMake.setAdapter(mPartAdapterMake);
        mRecyclerViewMake.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecyclerViewMake.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperMake = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                Part myPart = mPartAdapterMake.getPartAtPosition(mPosition);
                deletePartDialog(myPart, mTempCosplay);

            }
        });
        mItemTouchHelperMake.attachToRecyclerView(mRecyclerViewMake);
        mPartViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        mPartViewModel.getAllPartsToMake(mTempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                mPartAdapterMake.setParts(parts);
                mListMake.clear();
                mListMake.addAll(parts);
                updateCosplayPercentage();
            }
        });
        //recyclerview buy
        final RecyclerView mRecyclerViewBuy = mRoot.findViewById(R.id.RecView_PartsToBuy);
        mRecyclerViewBuy.setAdapter(mPartAdapterBuy);
        mRecyclerViewBuy.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecyclerViewBuy.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperBuy = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                Part myPart = mPartAdapterBuy.getPartAtPosition(mPosition);
                deletePartDialog(myPart, mTempCosplay);
            }
        });
        mItemTouchHelperBuy.attachToRecyclerView(mRecyclerViewBuy);
        mPartViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        mPartViewModel.getAllPartsToBuy(mTempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                mPartAdapterBuy.setParts(parts);
                mListBuy.clear();
                mListBuy.addAll(parts);
                updateCosplayPercentage();
            }
        });
        //fab to add a new cosplay part
        mFabAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCosplayPartDialog(mTempCosplay);
            }
        });
        //endregion

        //region Notes
        mCosplayNote.setText(mTempCosplay.mCosplayNote);
        mCosplayNotesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cosplay CosUP = new Cosplay(mTempCosplay.mCosplayId, mTempCosplay.mCosplayName, mTempCosplay.mCosplayStartDate, mTempCosplay.mCosplayEndDate, mTempCosplay.mCosplayBudget,mTempCosplay.mCosplayRemainingBudget, mTempCosplay.mCosplayIMG, mCosplayNote.getText().toString(),mTempCosplay.mNumberOfParts,mTempCosplay.mCosplayPercentage);

                mCosplayViewModel.update(CosUP);
                closeKeyboard(v);
                Toast.makeText(requireContext(), getResources().getText(R.string.NoteSaved), Toast.LENGTH_SHORT).show();
            }
        });
        //endregion
        //region RefImg
        setRefImageInGrid(mTempCosplay, mReferenceImgAdapter);
        mRefImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add cosplay img to db
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE,GALLERY_REQUEST_CODE_REF_IMG,true);
            }
        });
        //endregion

        //region webshops
        mWebshopAdapter = new WebshopAdapter(requireContext(), getActivity().getApplication());
        mRecViewCosplayWebshop.setAdapter(mWebshopAdapter);
        mRecViewCosplayWebshop.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecViewCosplayWebshop.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperWebshop = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                Webshop myWebshop = mWebshopAdapter.getWebshopAtPosition(mPosition);
                mWebshopViewModel.delete(myWebshop);
            }
        });
        mItemTouchHelperWebshop.attachToRecyclerView(mRecViewCosplayWebshop);
        mWebshopViewModel = new ViewModelProvider(this).get(WebshopViewModel.class);
        mWebshopViewModel.getAllWebshops(mTempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<Webshop>>() {
            @Override
            public void onChanged(List<Webshop> webshops) {
                mWebshopAdapter.setWebshops(webshops);
            }
        });
        mFabAddCosplayWebshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayWebshopDialog(mTempCosplay);
            }
        });
        //endregion

        //region CheckList
        mCheckListPartAdapter = new CheckListPartAdapter(requireContext(), getActivity().getApplication());
        mRecViewCheckListPart.setAdapter(mCheckListPartAdapter);
        mRecViewCheckListPart.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperCheckListPart = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                ChecklistPart myCheckListPart = mCheckListPartAdapter.getChecklistPartAtPosition(mPosition);
                deleteCheckListPartDialog(myCheckListPart);
            }
        });
        mItemTouchHelperCheckListPart.attachToRecyclerView(mRecViewCheckListPart);
        mCheckListPartViewModel = new ViewModelProvider(this).get(CheckListPartViewModel.class);
        final List<ChecklistPart> mAllCheckListParts = new LinkedList<>();
        mCheckListPartViewModel.getAllCheckListParts(mTempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<ChecklistPart>>() {
            @Override
            public void onChanged(List<ChecklistPart> checklistParts) {
                mCheckListPartAdapter.setCheckListParts(checklistParts);
                mAllCheckListParts.addAll(checklistParts);
            }
        });

        mCheckListPartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayChecklistPartDialog(mTempCosplay);
            }
        });
        mCheckListPartClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListClearCheckBoxes(mAllCheckListParts);
            }
        });
        mCheckListExportToPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.ComingSoon, Toast.LENGTH_SHORT).show();
            }
        });
        //endregion

        //region Shoppinglist
        mShoppingListPartAdapter = new ShoppingListPartAdapter(requireContext(), getActivity().getApplication());
        mRecViewShoppingList.setAdapter(mShoppingListPartAdapter);
        mRecViewShoppingList.setLayoutManager(new LinearLayoutManager(requireContext()));

        ItemTouchHelper mItemTouchHelperShoppingList = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                ShoppingListPart myShoppingList = mShoppingListPartAdapter.getShoppingListPartAtPosition(mPosition);
                deleteShoppingListPartDialog(myShoppingList);
            }
        });
        mItemTouchHelperShoppingList.attachToRecyclerView(mRecViewShoppingList);
        mShoppingListViewModel = new ViewModelProvider(this).get(ShoppingListPartViewModel.class);
        mShoppingListViewModel.getAllShoppingListParts(mTempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<ShoppingListPart>>() {
            @Override
            public void onChanged(List<ShoppingListPart> shoppingListParts) {
                mShoppingListPartAdapter.setShoppingListParts(shoppingListParts);
            }
        });
        mFabShoppingListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayShoppingListPartDialog(mTempCosplay);
            }
        });
        mShoppingListClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShoppingListPartAdapter.getShoppingListParts().size() != 0) {
                    deleteWholeShoppingListDialog(mShoppingListPartAdapter.getShoppingListPartAtPosition(0));
                }
            }
        });
        //endregion

        // region WIP Images
        setWipImagesInGrid(mTempCosplay, mWipImgAdapter);
        mWIPImgAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE,GALLERY_REQUEST_CODE_WIP_IMG,true);
            }
        });
        mWIPImgTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE,CAMERA_REQUEST_CODE_WIP_IMG,false);

            }
        });
        //endregion

        //region Events
        //Region Convention
        mRecViewEventsConvention.setAdapter(mEventConventionAdapter);
        mRecViewEventsConvention.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecViewEventsConvention.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperEventConvention = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                Event mCurrentEvent = mEventConventionAdapter.getEventAtPosition(mPosition);
                deleteEventDialog(mCurrentEvent);
            }
        });
        mItemTouchHelperEventConvention.attachToRecyclerView(mRecViewEventsConvention);
        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        mEventViewModel.getAllEvents(mTempCosplay.mCosplayId, "Convention").observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mEventConventionAdapter.setEvents(events);
            }
        });

        //Region Convention
        mRecViewEventsShoots.setAdapter(mEventShootAdapter);
        mRecViewEventsShoots.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecViewEventsShoots.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperEventShoot = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                Event mCurrentEvent = mEventShootAdapter.getEventAtPosition(mPosition);
                deleteEventDialog(mCurrentEvent);
            }
        });
        mItemTouchHelperEventShoot.attachToRecyclerView(mRecViewEventsShoots);
        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        mEventViewModel.getAllEvents(mTempCosplay.mCosplayId, "Photoshoot").observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mEventShootAdapter.setEvents(events);
            }
        });
        //Region Convention
        mRecViewEventsCharity.setAdapter(mEventCharityAdapter);
        mRecViewEventsCharity.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mRecViewEventsCharity.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper mItemTouchHelperEventCharity = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = viewHolder.getAdapterPosition();
                Event mCurrentEvent = mEventCharityAdapter.getEventAtPosition(mPosition);
                deleteEventDialog(mCurrentEvent);
            }
        });
        mItemTouchHelperEventCharity.attachToRecyclerView(mRecViewEventsCharity);
        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        mEventViewModel.getAllEvents(mTempCosplay.mCosplayId, "Charity").observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mEventCharityAdapter.setEvents(events);
            }
        });
        mFabEventsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCosplayEventDialog(mTempCosplay);
            }
        });

        //endregion

        return mRoot;
    }

    public void checkPermission(String permission, int requestCode,int GALLERY_REQUEST_CODE,boolean isGalleryRequest)
    {
        if (ContextCompat.checkSelfPermission(requireContext(), permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[] { permission },
                    requestCode);
        }
        else {
          /*  Toast.makeText(requireContext(),
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();*/
            if (isGalleryRequest)
            CreateIntent(GALLERY_REQUEST_CODE,true);
            else
                CreateIntent(CAMERA_REQUEST_CODE_WIP_IMG,false);
        }
    }
    private void CreateIntent(int galleryRequestCodeRefImg,boolean IsGalleryRequest) {


        if (IsGalleryRequest){
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_chooseImg_intent)), galleryRequestCodeRefImg);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE_WIP_IMG);
        }

    }

    public void checkListClearCheckBoxes(List<ChecklistPart> allParts) {
        for (ChecklistPart mPart : allParts) {
            mCheckListPartViewModel.update(new ChecklistPart(mPart.mCosplayId, mPart.mCosplayCheckListPartId, mPart.mCosplayCheckListPartName, false));
        }
    }

    public void setRefImageInGrid(Cosplay tempCosplay, final ReferenceImgAdapter mReferenceImgAdapter) {
        mReferenceImgViewModel = new ViewModelProvider(this).get(ReferenceImgViewModel.class);
        mReferenceImgViewModel.GetAllRefImg(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<ReferenceImg>>() {
            @Override
            public void onChanged(List<ReferenceImg> referenceImgs) {
                mReferenceImgAdapter.setRefImg(referenceImgs);
            }
        });
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false);
        mRVRefImg.setLayoutManager(mGridLayoutManager);
        mRVRefImg.setAdapter(mReferenceImgAdapter);
    }

    public void setWipImagesInGrid(Cosplay tempCosplay, final WIPImgAdapter mWipImgAdapter) {
        mWipImgViewModel = new ViewModelProvider(this).get(WIPImgViewModel.class);
        mWipImgViewModel.getAllWIPImgs(tempCosplay.mCosplayId).observe(getViewLifecycleOwner(), new Observer<List<WIPImg>>() {
            @Override
            public void onChanged(List<WIPImg> wipImgs) {
                mWipImgAdapter.setWIPImgs(wipImgs);
            }
        });
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false);
        mRecViewWIPImg.setLayoutManager(mGridLayoutManager);
        mRecViewWIPImg.setAdapter(mWipImgAdapter);
    }

    public void reloadEventsAdapter() {
        mEventConventionAdapter.notifyDataSetChanged();
        mEventShootAdapter.notifyDataSetChanged();
        mEventCharityAdapter.notifyDataSetChanged();
    }

    public void updateCosplayHeaderBudget() {
        double mPercentage = mTempCosplay.mCosplayRemainingBudget / mTempCosplay.mCosplayBudget * 100;
        if (mPercentage < 25 && mPercentage > 0) {
            mBudget.setTextColor(Color.YELLOW);
        } else if (mPercentage <= 0) {
            mBudget.setTextColor(Color.RED);
        } else {
            mBudget.setTextColor(Color.GREEN);

        }
        mBudget.setText(Double.toString(mTempCosplay.mCosplayRemainingBudget));
    }

    public void updateCosplayPercentage() {
        mTempCosplay.mNumberOfParts = mListBuy.size() + mListMake.size();
        double mPartFinished = 0;
        for (Part mPart : mListMake
        ) {
            if (mPart.mCosplayPartStatus.equals("Finished")) {
                mPartFinished++;
            }
        }
        for (Part mPart : mListBuy
        ) {
            if (mPart.mCosplayPartStatus.equals("Finished")) {
                mPartFinished++;
            }
        }
        if (!(mPartFinished == 0) && !(mTempCosplay.mNumberOfParts == 0)) {
            mTempCosplay.mCosplayPercentage = mPartFinished / mTempCosplay.mNumberOfParts * 100;
        } else {
            mTempCosplay.mCosplayPercentage = 0.0;
        }
        mCosplayViewModel.update(mTempCosplay);
        DecimalFormat mForm = new DecimalFormat("0.00");
        mPercentage.setText(String.format("%s %%", mForm.format(mTempCosplay.mCosplayPercentage)));
    }

    private void DatePickerDialog(View v, EditText editTextDate, DatePickerDialog.OnDateSetListener listener, boolean hasFocus) {
        if (hasFocus) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

            int mYear;
            int mMonth;
            int mDay;

            String mTempStartDate = editTextDate.getText().toString().trim();
            if (!checkDateFormat(mTempStartDate)) {
                Calendar mCalendar = Calendar.getInstance();
                mYear = mCalendar.get(Calendar.YEAR);
                mMonth = mCalendar.get(Calendar.MONTH);
                mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
            } else {
                String mDateComplete = editTextDate.getText().toString();
                String[] mDate = mDateComplete.split("/");
                mDay = Integer.parseInt(mDate[0].trim());
                mMonth = Integer.parseInt(mDate[1].trim());
                mYear = Integer.parseInt(mDate[2].trim());
                mMonth = mMonth - 1;
            }

            DatePickerDialog mDatePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, listener, mYear, mMonth, mDay);
            mDatePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDatePickerDialog.show();
        }
    }

    //All dialogs
    public void addNewCosplayWebshopDialog(final Cosplay cosplay) {
        final View WebshopPopUpView = getLayoutInflater().inflate(R.layout.cosplay_webshop, null);
        final EditText mSiteLink = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteLink);
        final EditText mSiteName = WebshopPopUpView.findViewById(R.id.EditText_NewCosplayWebsiteName);
        final Button mAdd = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteAdd);
        Button mCancel = WebshopPopUpView.findViewById(R.id.Btn_NewCosplayWebsiteCancel);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDialogBuilder.setView(WebshopPopUpView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSiteName.getText().toString().equals("") && !mSiteLink.getText().toString().equals("")) {
                    Webshop mTempWebshop = new Webshop(cosplay.mCosplayId, 0, mSiteName.getText().toString(), mSiteLink.getText().toString());
                    mWebshopViewModel.insert(mTempWebshop);
                    mDialog.dismiss();
                } else {
                    String tempString =getResources().getString(R.string.FillOutFields) + " " + getResources().getString(R.string.txtName) + ", " + getResources().getString(R.string.NewPart_LinkHint);
                    Toast.makeText(requireContext(), tempString, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addNewCosplayChecklistPartDialog(final Cosplay cosplay) {
        final View checkListPopUpView = getLayoutInflater().inflate(R.layout.cosplay_checklist_addpart, null);
        final EditText mCheckListPartName = checkListPopUpView.findViewById(R.id.EditText_NewChecklistPartName);
        final Button mChecklistAdd = checkListPopUpView.findViewById(R.id.Btn_NewChecklistPartAdd);

        Button mCheckListCancel = checkListPopUpView.findViewById(R.id.Btn_NewChecklistPartCancel);

        mDialogBuilder = new AlertDialog.Builder(requireContext());

        mDialogBuilder.setView(checkListPopUpView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mCheckListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mChecklistAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCheckListPartName.getText().toString().equals("")) {
                    ChecklistPart mTempCheckListPart = new ChecklistPart(cosplay.mCosplayId, 0, mCheckListPartName.getText().toString(), false);
                    mCheckListPartViewModel.insert(mTempCheckListPart);
                    mDialog.dismiss();
                } else {

                    Toast.makeText(requireContext(), getResources().getString(R.string.FillOutAllFields), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addNewCosplayShoppingListPartDialog(final Cosplay cosplay) {
        final View shoppingListPopUpView = getLayoutInflater().inflate(R.layout.cosplay_shoppinglist_add, null);
        Button mShoppingListCancel = shoppingListPopUpView.findViewById(R.id.Btn_NewShoppingListCancel);
        Button mShoppinglistAdd = shoppingListPopUpView.findViewById(R.id.Btn_NewShoppingListAdd);
        final EditText mShoppingListPartName = shoppingListPopUpView.findViewById(R.id.EditText_NewShoppingListName);
        final EditText mShoppingListShop = shoppingListPopUpView.findViewById(R.id.EditText_NewShoppingListShop);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDialogBuilder.setView(shoppingListPopUpView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mShoppingListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mShoppinglistAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListPart temp = new ShoppingListPart(cosplay.mCosplayId, 0, mShoppingListPartName.getText().toString(), mShoppingListShop.getText().toString(), false);
                mShoppingListViewModel.insert(temp);
                mDialog.dismiss();
            }
        });
    }

    public void AddNewCosplayPartDialog(final Cosplay mCosplay) {
        final View PartPopUpView = getLayoutInflater().inflate(R.layout.add_cosplay_part, null);
        Button mPartChooseImage = PartPopUpView.findViewById(R.id.Btn_NewPartChoosePartImg);
        Button mPartCancel = PartPopUpView.findViewById(R.id.Btn_NewPartCancel);
        Button mPartAddPart = PartPopUpView.findViewById(R.id.Btn_NewPartAdd);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mPartName = PartPopUpView.findViewById(R.id.EditText_NewPartName);
        mSpinnerPartMakeBuy = PartPopUpView.findViewById(R.id.Spinner_NewPartBuyMake);
        if (mSpinnerPartMakeBuy != null) {
            mSpinnerPartMakeBuy.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> mArrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.BuyMake, android.R.layout.simple_spinner_item);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPartMakeBuy.setAdapter(mArrayAdapter);
        mPartLink = PartPopUpView.findViewById(R.id.EditText_NewPartLink);
        mPartEndDate = PartPopUpView.findViewById(R.id.EditText_NewPartEndDate);
        mPartCost = PartPopUpView.findViewById(R.id.EditText_NewPartCost);
        mPartImage = PartPopUpView.findViewById(R.id.ImageView_NewPartImgPreview);
        mDialogBuilder.setView(PartPopUpView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        //create dateSelector and add the selected date to the Edit text
        mPartEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog(v, mPartEndDate, mEndDateSetListener, hasFocus);
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mPartEndDate.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
            }
        };

        //Cancel. dismiss the popup
        mPartCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        //Choose the picture from the gallery
        mPartChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE,GALLERY_REQUEST_CODE_PART,true);

            }
        });
        mPartAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPartName.getText().toString().equals("")&&mPartUri!=null) {
                    double mCost;
                    if (!mPartCost.getText().toString().equals("")) {
                        mCost = Double.parseDouble(mPartCost.getText().toString());
                    } else {
                        mCost = 0.0;
                    }
                    Part temp = new Part(mCosplay.mCosplayId, 0, mPartName.getText().toString(), mSpinnerPartMakeBuy.getSelectedItem().toString(), mPartLink.getText().toString(), mCost, "Planned", mPartEndDate.getText().toString(),mPartUri );
                    mPartViewModel.insert(temp);
                    mCosplay.mCosplayRemainingBudget -= temp.mCosplayPartCost;
                    mCosplayViewModel.update(mCosplay);
                    updateCosplayHeaderBudget();
                    updateCosplayPercentage();
                    mDialog.dismiss();
                } else {
                    String tempString = getResources().getString(R.string.FillOutFields) + " " + getResources().getString(R.string.txtName)+", image";
                    Toast.makeText(requireContext(), tempString, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void addNewCosplayEventDialog(final Cosplay tempCosplay) {
        final View mEventDialog = getLayoutInflater().inflate(R.layout.events_dialog, null);
        final Spinner mEventType = mEventDialog.findViewById(R.id.Spinner_NewEventType);
        final EditText mEventName = mEventDialog.findViewById(R.id.EditText_NewEventName);
        final EditText mEventPlace = mEventDialog.findViewById(R.id.EditText_NewEventPlace);
        final EditText mEventStartDate = mEventDialog.findViewById(R.id.EditText_NewEventBeginDate);
        final EditText mEventEndDate = mEventDialog.findViewById(R.id.EditText_NewEventEndDate);
        Button mEventAdd = mEventDialog.findViewById(R.id.Btn_NewEventAdd);
        Button mEventCancel = mEventDialog.findViewById(R.id.Btn_NewEventCancel);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        if (mEventType != null) {
            mEventType.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> mEventArrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.EventType, android.R.layout.simple_spinner_item);
        mEventArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (mEventType != null) {
            mEventType.setAdapter(mEventArrayAdapter);
        }

        mDialogBuilder.setView(mEventDialog);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        //region DateListener
        //create dateSelector and add the selected date to the Edit text
        mEventStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog(v, mEventStartDate, mStartDateSetListener, hasFocus);
            }
        });
        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mEventStartDate.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
            }
        };
        //create dateSelector and add the selected date to the Edit text
        mEventEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog(v, mEventEndDate, mEndDateSetListener, hasFocus);
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mEventEndDate.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
            }
        };
        //endregion

        mEventCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mEventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEventName.getText().toString().equals("") && !mEventStartDate.getText().toString().equals("") && !mEventEndDate.getText().toString().equals("")) {
                    Event mTempEvent = null;
                    if (mEventType != null) {
                        mTempEvent = new Event(tempCosplay.mCosplayId, 0, mEventName.getText().toString(), mEventPlace.getText().toString(), mEventStartDate.getText().toString(), mEventEndDate.getText().toString(), mEventType.getSelectedItem().toString());
                    }
                    mEventViewModel.insert(mTempEvent);
                    mDialog.dismiss();
                } else {
                    String tempString =getResources().getString(R.string.FillOutFields) + " " + getResources().getString(R.string.txtName)+ ", " + getResources().getString(R.string.txtStartDate) + ", " + getResources().getString(R.string.txtEndDate);
                    Toast.makeText(requireContext(), tempString, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void deleteEventDialog(final Event mEvent) {
        final View mDeleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        Button mBtnCancel = mDeleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        Button mBtnDelete = mDeleteCosplayView.findViewById(R.id.Btn_DeleteDelete);

        TextView mDeleteText = mDeleteCosplayView.findViewById(R.id.TextView_DeleteTitle);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart)+" " + mEvent.mCosplayEventName);
        mDialogBuilder.setView(mDeleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventViewModel.delete(mEvent);
                Toast.makeText(requireContext(), mEvent.mCosplayEventName + getResources().getString(R.string.imageDelete), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                reloadEventsAdapter();
            }

        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                reloadEventsAdapter();
            }
        });
    }

    public void deleteShoppingListPartDialog(final ShoppingListPart mShoppingListPart) {
        final View mDeleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = mDeleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        Button mBtnCancel = mDeleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        Button mBtnDelete = mDeleteCosplayView.findViewById(R.id.Btn_DeleteDelete);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart )+" "+ mShoppingListPart.mCosplayShoppingListPartName);
        mDialogBuilder.setView(mDeleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShoppingListViewModel.delete(mShoppingListPart);
                Toast.makeText(requireContext(), mShoppingListPart.mCosplayShoppingListPartName + getResources().getString(R.string.imageDelete), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                mShoppingListPartAdapter.notifyDataSetChanged();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mShoppingListPartAdapter.notifyDataSetChanged();
            }
        });
    }

    public void deleteCheckListPartDialog(final ChecklistPart mCheckListPart) {
        final View mDeleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        TextView mDeleteText = mDeleteCosplayView.findViewById(R.id.TextView_DeleteTitle);
        Button mBtnCancel = mDeleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        Button mBtnDelete = mDeleteCosplayView.findViewById(R.id.Btn_DeleteDelete);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart)+" " + mCheckListPart.mCosplayCheckListPartName);
        mDialogBuilder.setView(mDeleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckListPartViewModel.delete(mCheckListPart);
                Toast.makeText(requireContext(), mCheckListPart.mCosplayCheckListPartName + getResources().getString(R.string.imageDelete), Toast.LENGTH_SHORT).show();
                mCheckListPartAdapter.notifyDataSetChanged();
                mDialog.dismiss();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mCheckListPartAdapter.notifyDataSetChanged();
            }
        });
    }
    public void deleteWholeShoppingListDialog(final ShoppingListPart part) {
        final View deleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        Button mBtnCancel = deleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        Button mBtnDelete = deleteCosplayView.findViewById(R.id.Btn_DeleteDelete);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDeleteText.setText(getString(R.string.ConformationDeleteCheckListPart)+" " + getString(R.string.WholeList));

        mDialogBuilder.setView(deleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShoppingListViewModel.deleteAll(part);
                Toast.makeText(requireContext(),getResources().getText(R.string.ShoppingLIstDeleted), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                mShoppingListPartAdapter.notifyDataSetChanged();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void deletePartDialog(final Part myPart, final Cosplay cosplay) {
        final View deleteCosplayView = getLayoutInflater().inflate(R.layout.delete, null);
        Button mBtnCancel = deleteCosplayView.findViewById(R.id.Btn_DeleteCancel);
        Button mBtnDelete = deleteCosplayView.findViewById(R.id.Btn_DeleteDelete);
        TextView mDeleteText = deleteCosplayView.findViewById(R.id.TextView_DeleteTitle);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mDeleteText.setText(getResources().getText(R.string.ConformationDeleteCheckListPart)+" " + myPart.mCosplayPartName);
        mDialogBuilder.setView(deleteCosplayView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPartViewModel.delete(myPart);
                Toast.makeText(requireContext(), myPart.mCosplayPartName +getResources().getText( R.string.imageDelete), Toast.LENGTH_SHORT).show();
                cosplay.mCosplayRemainingBudget += myPart.mCosplayPartCost;

                mCosplayViewModel.update(cosplay);
                updateCosplayHeaderBudget();
                updateCosplayPercentage();

                mDialog.dismiss();
                mPartAdapterBuy.notifyDataSetChanged();
                mPartAdapterMake.notifyDataSetChanged();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mPartAdapterBuy.notifyDataSetChanged();
                mPartAdapterMake.notifyDataSetChanged();
            }
        });
    }

    public void UpdateCosplayDialog(final Cosplay cosplay) {
        @SuppressLint("InflateParams") final View[] cosplayPopUpView = {getLayoutInflater().inflate(R.layout.add_cosplay, null)};
        Button mChoosePicture = cosplayPopUpView[0].findViewById(R.id.Btn_NewCosplayChooseImg);
        Button mCancel = cosplayPopUpView[0].findViewById(R.id.Btn_NewCosplayCancel);
        Button mUpdateCosplays = cosplayPopUpView[0].findViewById(R.id.Btn_NewCosplayAdd);

        mDialogBuilder = new AlertDialog.Builder(requireContext());
        mCosplayName = cosplayPopUpView[0].findViewById(R.id.EditText_NewCosplayName);
        mCosplayStartDate = cosplayPopUpView[0].findViewById(R.id.EditText_NewCosplayBeginDate);
        mCosplayEndDate = cosplayPopUpView[0].findViewById(R.id.EditText_NewCosplayEndDate);
        mCosplayBudget = cosplayPopUpView[0].findViewById(R.id.EditText_NewCosplayBudget);
        mCosplayImage = cosplayPopUpView[0].findViewById(R.id.ImageView_NewCosplayImgPreview);
        mDialogBuilder.setView(cosplayPopUpView[0]);

        mUpdateCosplays.setText(R.string.UpdateCosplay);
        mCosplayName.setText(cosplay.mCosplayName);
        mCosplayStartDate.setText(cosplay.mCosplayStartDate);
        mCosplayEndDate.setText(cosplay.mCosplayEndDate);
        mCosplayBudget.setText(Double.toString(cosplay.mCosplayBudget));
        SetImageFromUri(mCosplayImage,cosplay.mCosplayIMG);
        mDialog = mDialogBuilder.create();
        mDialog.show();
        //region Date selector
        //create date Selector and add the selected date to the Edit text
        mCosplayStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog(v, mCosplayStartDate, mStartDateSetListener, hasFocus);

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
                DatePickerDialog(v, mCosplayEndDate, mEndDateSetListener, hasFocus);
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mCosplayEndDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
        //endregion

        //Cancel. dismiss the popup
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        //Choose the picture from the gallery

        mChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE,GALLERY_REQUEST_CODE,true);
            }
        });
        //Add Cosplay to the database
        mUpdateCosplays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mTempExpenses = cosplay.mCosplayBudget - cosplay.mCosplayRemainingBudget;
                double mCost;
                if (!mCosplayBudget.getText().toString().equals("")) {
                    mCost = Double.parseDouble(mCosplayBudget.getText().toString());
                } else {
                    mCost = 0.0;
                }
                String mPath=cosplay.mCosplayIMG;
                if(mCosplayUri!=null){
                    mPath=mCosplayUri;
                }


                Cosplay mCosUpdate = new Cosplay(cosplay.mCosplayId, mCosplayName.getText().toString(), mCosplayStartDate.getText().toString(), mCosplayEndDate.getText().toString(), mCost, mCost - mTempExpenses, mPath, mCosplayNote.getText().toString(),cosplay.mNumberOfParts,cosplay.mCosplayPercentage);

                mCosplayViewModel.update(mCosUpdate);
                mDialog.dismiss();
                mTempCosplay = mCosUpdate;
                mName.setText(mCosUpdate.mCosplayName);
                mEndDate.setText(mCosUpdate.mCosplayEndDate);
                updateCosplayHeaderBudget();
                SetImageFromUri(mImage,mCosUpdate.mCosplayIMG);
                mPercentage.setText(String.format("%s%%", mCosUpdate.mCosplayPercentage));
                mCosplayNote.setText(mCosUpdate.mCosplayNote);

            }
        });

    }

    private void closeKeyboard(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri mImageData;
        if (requestCode == GALLERY_REQUEST_CODE && data != null) {
            mImageData = data.getData();
            mCosplayUri=getPathFromUri(mImageData);
            SetImageFromUri(mCosplayImage,mCosplayUri);
        }
        if (requestCode == GALLERY_REQUEST_CODE_PART && data != null) {
            mImageData = data.getData();
            mPartUri=getPathFromUri(mImageData);
            SetImageFromUri(mPartImage,mPartUri);
        }
        if (requestCode == GALLERY_REQUEST_CODE_REF_IMG && data != null) {
            mImageData = data.getData();
            InputStream mImageStream = null;
            try {
                mImageStream = getContext().getContentResolver().openInputStream(mImageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ReferenceImg temp = new ReferenceImg(mTempCosplay.mCosplayId, 0, getPathFromUri(mImageData));
            mReferenceImgViewModel.insert(temp);
            setRefImageInGrid(mTempCosplay, mReferenceImgAdapter);
        }
        if (requestCode == GALLERY_REQUEST_CODE_WIP_IMG && data != null) {
            mImageData = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContext().getContentResolver().openInputStream(mImageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            WIPImg temp = new WIPImg(mTempCosplay.mCosplayId, 0, getPathFromUri(mImageData));
            mWipImgViewModel.insert(temp);
            setWipImagesInGrid(mTempCosplay, mWipImgAdapter);
        }
        if (requestCode == CAMERA_REQUEST_CODE_WIP_IMG && data != null) {
            Bitmap img = (Bitmap) data.getExtras().get("data");

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                // Requesting the permission
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        102);
            }
            else {
                OutputStream mOutputStream=null;
                File mFilePath= Environment.getExternalStorageDirectory();
                File dir=new File(mFilePath.getAbsolutePath()+"/WIPImg/");
                dir.mkdir();
                String fileName="WIP"+System.currentTimeMillis()+".jpg";
                File file=new File(dir,fileName);
                try {
                    mOutputStream=new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                img.compress(Bitmap.CompressFormat.JPEG,100,mOutputStream);
                WIPImg temp = new WIPImg(mTempCosplay.mCosplayId, 0,mFilePath.getAbsolutePath()+"/WIPImg/"+fileName );
                mWipImgViewModel.insert(temp);
                setWipImagesInGrid(mTempCosplay, mWipImgAdapter);
                try {
                    mOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public void SetImageFromUri(ImageView mImageView,String mImagePath){
        Uri selectedImageUri=null;
        if (mImagePath != null) {
            File f = new File(mImagePath);
            selectedImageUri = Uri.fromFile(f);
        }
        Bitmap mBitmap=null;
        try {
            mBitmap= BitmapFactory.decodeStream(requireContext().getContentResolver().openInputStream(selectedImageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImageView.setImageBitmap(mBitmap);
    }
    public Boolean checkDateFormat(String date) {
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            mFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public String getPathFromUri(Uri mContentUri){
        String res=null;
        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor=getContext().getContentResolver().query(mContentUri,proj,null,null,null  );
        if (cursor.moveToFirst()){
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res=cursor.getString(column_index);
        }
        cursor.close();
        return res;
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