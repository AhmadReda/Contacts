package com.example.contacts;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> implements Filterable {

    private ArrayList<ContactData> contacts;
    private ArrayList<ContactData> contactsFilter;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(ArrayList<ContactData> contacts) {
        this.contacts = contacts;
        this.contactsFilter = new ArrayList<>(contacts);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contacts_layout,parent,false);
        return new ContactsViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.initContactData(contacts.get(position));
        holder.contactContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DetialActivity.class);
                intent.putExtra(ContactConstants.NAME,contacts.get(position).getName());
                intent.putExtra(ContactConstants.MOBILE_PHONE,contacts.get(position).phone.getPhoneMobile());
                intent.putExtra(ContactConstants.EMAIL,contacts.get(position).getEmail());
                intent.putExtra(ContactConstants.ADDRESS,contacts.get(position).getAddress());
                intent.putExtra(ContactConstants.GENDER,contacts.get(position).getGender());
                intent.putExtra(ContactConstants.OFFICE_PHONE,contacts.get(position).phone.getPhoneOffice());
                intent.putExtra(ContactConstants.HOME_PHONE,contacts.get(position).phone.getPhoneHome());
                view.getContext().startActivity(intent);
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //Run on background Thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ContactData> FilteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                FilteredList.addAll(contactsFilter);
            }
            else {
                for(ContactData contactData : contactsFilter){
                    if(contactData.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        FilteredList.add(contactData);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = FilteredList;
            return filterResults;
        }
        //Run on UI Thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contacts.clear();
            contacts.addAll((Collection<? extends ContactData>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ContactsViewHolder extends RecyclerView.ViewHolder {

        private TextView contactName;
        private TextView contactPhoneMobile;
        private TextView circleName;
        private ConstraintLayout contactContainer;

        // each data item is just a string in this case
        public ContactsViewHolder(View v) {
            super(v);
            contactName = v.findViewById(R.id.contact_name);
            contactPhoneMobile = v.findViewById(R.id.contact_phone_mobile);
            circleName = v.findViewById(R.id.text_name);
            contactContainer = v.findViewById(R.id.contact_container);
        }

        public void initContactData(ContactData contact){
            contactName.setText(contact.getName());
            contactPhoneMobile.setText(contact.phone.getPhoneHome());
            circleName.setText(contact.getName().substring(0,1));
        }
    }
}
