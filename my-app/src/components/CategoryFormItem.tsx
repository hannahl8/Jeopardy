// src/components/CategoryFormItem.tsx
import React from 'react';
import { FieldArray, useFormikContext } from 'formik';
import TextInput from './form/TextInput';
import ClueFormItem from './ClueFormItem';
import { GameRequest } from '../types';

interface CategoryFormItemProps {
    index: number;
}

const CategoryFormItem: React.FC<CategoryFormItemProps> = ({ index }) => {
    const { values } = useFormikContext<GameRequest>();

    return (
        <div  className="category-div container">
            <h2>Category {index + 1}</h2>
            <TextInput label="" name={`categoryRequests[${index}].name`} />
            <FieldArray name={`categoryRequests[${index}].clueRequests`}>
                {() => (
                    <>
                        {values.categoryRequests[index].clueRequests.map((_clue, clueIndex) => (
                            <ClueFormItem key={clueIndex} index={clueIndex} categoryIndex={index} />
                        ))}
                    </>
                )}
            </FieldArray>
        </div>
    );
};

export default CategoryFormItem;