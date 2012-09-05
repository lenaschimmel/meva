package de.gmino.checkin.android.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.gmino.checkin.android.CheckinApplication;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Consumer;
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.checkin.shared.domain.CouponOwenership;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Coupons extends ActivityWithFacebook {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	ArrayList<Coupon> loadedCoupons;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadedCoupons = new ArrayList<Coupon>();

		setContentView(R.layout.coupons);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		if (getIntent() != null && getIntent().getExtras() != null) {
			final long shopId = getIntent().getExtras().getLong("shopId");

			Requests.getLoadedEntityById(Shop.type, shopId,
					new RequestListener<Shop>() {
						@Override
						public void onNewResult(Shop shop) {
							getActionBar().setTitle(
									"Coupons bei " + shop.getTitle());
							Collection<Coupon> coupons = shop.getCoupons();
							Requests.loadEntities(coupons,
									new RequestListener<Coupon>() {
										@Override
										public void onNewResult(Coupon result) {
											loadedCoupons.add(result);
											mSectionsPagerAdapter
													.notifyDataSetChanged();
										}
									});
						}
					});
		} else {
			getActionBar().setTitle("Meine Coupons");

			Consumer me = ((CheckinApplication) getApplication()).getConsumer();
			Collection<CouponOwenership> myCouponOwnerships = me
					.getCouponOwnerships();

			if (myCouponOwnerships.isEmpty()) {
				// TODO show a permanent message
				Toast.makeText(this, "Du hast keine Coupons", Toast.LENGTH_LONG);
			} else {
				Requests.loadEntities(myCouponOwnerships,
						new RequestListener<CouponOwenership>() {
							Collection<Coupon> coupons = new HashSet<Coupon>();

							@Override
							public void onNewResult(CouponOwenership result) {
								coupons.add((Coupon) result.getCoupon());
							}

							@Override
							public void onFinished(
									Collection<CouponOwenership> results) {
								Requests.loadEntities(coupons,
										new RequestListener<Coupon>() {
											@Override
											public void onNewResult(
													Coupon result) {
												loadedCoupons.add(result);
												mSectionsPagerAdapter
														.notifyDataSetChanged();
											}
										});
							}
						});
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return loadedCoupons.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return loadedCoupons.get(position).getTitle();
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public class DummySectionFragment extends Fragment {
		public DummySectionFragment() {
		}

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			LinearLayout linearlayout = (LinearLayout) inflater.inflate(
					R.layout.coupon, null);
			linearlayout.setGravity(Gravity.CENTER);
			linearlayout.setBackgroundResource(R.drawable.cpn);
			Bundle args = getArguments();
			TextView textView = (TextView) linearlayout
					.findViewById(R.id.textView1);
			textView.setText(getGutscheinText(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return linearlayout;
		}
	}

	public CharSequence getGutscheinText(int position) {
		return loadedCoupons.get(position).getDescription();
	}
}
